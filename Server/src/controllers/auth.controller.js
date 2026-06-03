// Server/src/controllers/auth.controller.js
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import { prisma } from '../config/db.js';
import generateToken from '../utils/generateToken.js';

export const login = async (req, res) => {
    try {
        const { email, password, role } = req.body;

        const user = await prisma.user.findUnique({ where: { email } });

        if (!user || user.role !== role) {
            return res.status(404).json({ message: "Invalid credentials or unauthorized role" });
        }

        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) {
            return res.status(400).json({ message: "Invalid credentials - Password is incorrect" });
        }

        const responsePayload = {
            id: user.id,
            firstName: user.firstName,
            lastName: user.lastName,
            email: user.email,
            role: user.role,
        };

        const token = generateToken(user);

        res.status(200).json({
            message: "Login successful",
            token,
            user: responsePayload,
        });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const register = async (req, res) => {
    try {
        const { firstName, lastName, email, password, role } = req.body;

        const existingUser = await prisma.user.findUnique({ where: { email } });
        if (existingUser) {
            return res.status(400).json({ message: "User with this email already exists" });
        }

        const salt = await bcrypt.genSalt(10);
        const hashedPassword = await bcrypt.hash(password, salt);

        const user = await prisma.user.create({
            data: {
                firstName,
                lastName,
                email,
                password: hashedPassword,
                role
            },
        });

        const token = generateToken(user);

        res.status(201).json({
            message: "User successfully created",
            token,
            user: {
                id: user.id,
                firstName: user.firstName,
                lastName: user.lastName,
                email: user.email,
                role: user.role,
            },
        });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const protect = (req, res, next) => {
    const token = req.headers.authorization?.split(' ')[1];

    if (!token) return res.status(401).json({ message: "Not authorized, no token" });

    try {
        const decoded = jwt.verify(token, process.env.JWT_SECRET);
        req.user = decoded;
        next();
    } catch (error) {
        res.status(401).json({ message: "NOT AUTHORIZED, token validation failed" });
    }
};

export const updatePatientInfo = async (req, res) => {
    try {
        const { phoneNumber, age, gender, bloodType, height, weight, allergies, emergencyContact } = req.body;
        const userId = req.user.id;

        if (!userId) return res.status(400).json({ message: "User Identification missing" });

        const updatedUser = await prisma.user.update({
            where: { id: userId },
            data: {
                phoneNumber,
                age,
                gender,
                bloodType,
                height,
                weight,
                allergies: allergies || [],
                emergencyContact: emergencyContact || {}
            }
        });

        res.status(200).json({
            message: "Health profile updated successfully",
            user: {
                id: updatedUser.id,
                firstName: updatedUser.firstName,
                lastName: updatedUser.lastName,
                email: updatedUser.email,
                role: updatedUser.role,
                phoneNumber: updatedUser.phoneNumber,
                bloodType: updatedUser.bloodType,
                emergencyContact: updatedUser.emergencyContact
            }
        });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};