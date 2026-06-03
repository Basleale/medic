// Server/src/controllers/patient.controller.js
import bcrypt from "bcryptjs";
import { prisma } from '../config/db.js';
import generateToken from '../utils/generateToken.js';

export const patientInfo = async (req, res) => {
    try {
        const userId = req.user.id;
        if (req.user.role !== "Patient") return res.status(403).json({ message: "Forbidden" });

        const patient = await prisma.user.findUnique({
            where: { id: userId },
            select: { id: true, firstName: true, lastName: true, email: true, phoneNumber: true, age: true, gender: true, bloodType: true, height: true, weight: true, allergies: true, emergencyContact: true }
        });

        if (!patient) return res.status(404).json({ message: "Profile not found!" });
        res.status(200).json({ message: "Success", patient });
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

export const updatePatientInfo = async (req, res) => {
    try {
        const userId = req.user.id;
        const { firstName, lastName, phoneNumber, age, height, gender, weight, bloodType, allergies, emergencyContact } = req.body;

        const updatedPatient = await prisma.user.update({
            where: { id: userId },
            data: {
                firstName, lastName, phoneNumber, age: parseInt(age), bloodType, gender, height: parseFloat(height), weight: parseFloat(weight), allergies,
                emergencyContact: emergencyContact || {}
            }
        });

        res.status(200).json({ message: "Updated", user: updatedPatient });
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};

export const changePatientPassword = async (req, res) => {
    try {
        const { newPassword, oldPassword } = req.body;
        const userId = req.user.id;

        const patient = await prisma.user.findUnique({ where: { id: userId } });
        const isMatch = await bcrypt.compare(oldPassword, patient.password);
        if (!isMatch) return res.status(403).json({ message: "Incorrect password" });

        const hashedPassword = await bcrypt.hash(newPassword, 10);
        await prisma.user.update({ where: { id: userId }, data: { password: hashedPassword } });

        res.status(200).json({ message: "Success", token: generateToken(patient) });
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};