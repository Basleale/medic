import bcrypt from "bcryptjs";
import { prisma } from '../config/db.js';

export const getAllDoctors = async (req,res) => {
    try {
        const userId = req.user?.id;

        if(!userId) {
            return res.status(400).json({ success: false, message: "User Identification Missing" });
        }

        const doctors = await prisma.user.findMany({
            where: {
                role: "Doctor",
                isActive: true
            },
            select: {
                id: true,
                firstName: true,
                lastName: true,
                role: true,
                department: true,
                hourlyRate: true,
                rating: true,
                specialization: true,
                yearsOfExperience: true
            },
            orderBy: [
                { department: 'asc' },
                { firstName: 'asc' }
            ]
        });

        return res.status(200).json({
            success: true,
            message: "Successfully got all doctors",
            count: doctors.length,
            doctors
        });

    } catch(error) {
        console.log("Error fetching doctors:", error);
        return res.status(500).json({ success: false, message: "Failed to fetch doctors", error: error.message });
    }
}

export const createDoctor = async (req, res) => {
    try {
        const { email, password, firstName, lastName, department, specialization, yearsOfExperience, hourlyRate, staffID, rating } = req.body;

        if (!email || !password || !firstName || !lastName || !department || !staffID) {
            return res.status(400).json({ success: false, message: "Missing fields" });
        }

        const existingDoctor = await prisma.user.findFirst({
            where: {
                OR: [
                    { email: email.toLowerCase() },
                    { staffID }
                ]
            }
        });

        if (existingDoctor) {
            return res.status(409).json({ success: false, message: "Email or staff ID already exists" });
        }

        const hashedPassword = await bcrypt.hash(password, 10);

        const doctor = await prisma.user.create({
            data: {
                email: email.toLowerCase(),
                password: hashedPassword,
                firstName,
                lastName,
                role: "Doctor",
                department,
                specialization: specialization || "",
                yearsOfExperience: yearsOfExperience || 0,
                hourlyRate: hourlyRate || 100,
                staffID,
                rating: rating || 0
            }
        });

        return res.status(201).json({ success: true, message: "Doctor created successfully", doctor });

    } catch (error) {
        return res.status(500).json({ success: false, error: error.message });
    }
};

export const createDoctorAvailability = async (req, res) => {
    try {
        const { doctor, startDate, endDate, weeklySchedule, blackoutDates } = req.body;

        if (!doctor || !startDate || !endDate || !weeklySchedule) {
            return res.status(400).json({ success: false, message: "Missing required fields" });
        }

        const doctorExists = await prisma.user.findFirst({
            where: { id: doctor, role: "Doctor" }
        });

        if (!doctorExists) return res.status(404).json({ success: false, message: "Doctor not found" });

        const existingAvailability = await prisma.availability.findUnique({
            where: { doctorId: doctor }
        });

        if (existingAvailability) return res.status(409).json({ success: false, message: "Doctor already has an availability schedule" });

        const availability = await prisma.availability.create({
            data: {
                doctorId: doctor,
                startDate: new Date(startDate),
                endDate: new Date(endDate),
                weeklySchedule,
                blackoutDates: blackoutDates ? blackoutDates.map(d => new Date(d)) : []
            }
        });

        return res.status(201).json({ success: true, availability });

    } catch (error) {
        return res.status(500).json({ success: false, error: error.message });
    }
};