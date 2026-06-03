// Server/src/controllers/appointment.controller.js
import { prisma } from '../config/db.js';

export const getAppointments = async (req, res) => {
    try {
        const userId = req.user?.id;
        const role = req.user.role;

        let query = {};
        if (role === "Patient") query = { patientId: userId };
        else if (role === "Doctor") query = { doctorId: userId };
        else return res.status(403).json({ message: "INVALID ROLE permissions" });

        const appointments = await prisma.appointment.findMany({
            where: query,
            orderBy: { startTime: 'asc' },
            include: {
                patient: {
                    select: { firstName: true, lastName: true, email: true, phoneNumber: true, gender: true, age: true, bloodType: true }
                },
                doctor: {
                    select: { firstName: true, lastName: true, email: true, department: true, staffID: true }
                }
            }
        });

        return res.status(200).json({ success: true, count: appointments.length, appointments });
    } catch (err) {
        res.status(500).json({ success: false, message: "Failed to fetch appointments", error: err.message });
    }
};

export const createAppointment = async (req, res) => {
    try {
        const { doctor, startTime, endTime, reason, vistType, location } = req.body;
        const patientId = req.user?.id;

        if (req.user.role !== "Patient") return res.status(403).json({ success: false, message: "Access denied." });

        const start = new Date(startTime);
        const end = new Date(endTime);

        const overlappingAppointment = await prisma.appointment.findFirst({
            where: {
                doctorId: doctor,
                status: { in: ["Confirmed", "Rescheduled"] },
                OR: [
                    { startTime: { gte: start, lt: end } },
                    { endTime: { gt: start, lte: end } },
                    { startTime: { lte: start }, endTime: { gte: end } }
                ]
            }
        });

        if (overlappingAppointment) return res.status(409).json({ success: false, message: "Doctor is booked." });

        const newAppointment = await prisma.appointment.create({
            data: {
                patientId,
                doctorId: doctor,
                startTime: start,
                endTime: end,
                reason,
                vistType: vistType || "In-Person",
                location: location || "Main Clinic",
                status: "Confirmed"
            }
        });

        return res.status(201).json({ success: true, message: "Appointment booked!", appointment: newAppointment });
    } catch (error) {
        return res.status(500).json({ success: false, message: "Server error", error: error.message });
    }
};

export const cancelAppointment = async (req, res) => {
    try {
        const patientId = req.user?.id;
        const { appointmentId } = req.params;

        const appointment = await prisma.appointment.findUnique({ where: { id: appointmentId } });

        if (!appointment) return res.status(404).json({ success: false, message: "Appointment not found" });
        if (appointment.patientId !== patientId) return res.status(403).json({ success: false, message: "Not your appointment" });

        const updated = await prisma.appointment.update({
            where: { id: appointmentId },
            data: { status: "Cancelled" }
        });

        return res.status(200).json({ success: true, message: "Cancelled", cancelledAppointment: updated });
    } catch (error) {
        return res.status(500).json({ success: false, message: "Unable to cancel", error: error.message });
    }
};

export const RescheduleAppointment = async (req, res) => {
    // Similar Prisma logic, update using prisma.appointment.update()
    try {
        const { appointmentId } = req.params;
        const { startTime, endTime } = req.body;
        const patientId = req.user?.id;

        const updated = await prisma.appointment.update({
            where: { id: appointmentId },
            data: { startTime: new Date(startTime), endTime: new Date(endTime), status: "Rescheduled" }
        });

        return res.status(200).json({ success: true, message: "Rescheduled", appointment: updated });
    } catch (error) {
        return res.status(500).json({ success: false, error: error.message });
    }
};

export const deleteAppointment = async (req, res) => {
    try {
        const { appointmentId } = req.params;
        const deleted = await prisma.appointment.delete({ where: { id: appointmentId } });
        return res.status(200).json({ success: true, deletedAppointment: deleted });
    } catch (error) {
        return res.status(500).json({ success: false, error: error.message });
    }
};