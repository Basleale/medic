import { prisma } from '../config/db.js';

export const getDoctorAvaliablity = async (req, res) => {
    try {
        const { doctorId } = req.params;
        const availibility = await prisma.availability.findUnique({
            where: { doctorId }
        });

        if (!availibility) {
            return res.status(404).json({ success: false, message: "Doctor availibility not found" });
        }

        return res.status(200).json({ success: true, doctorId, availibility });
    } catch(error) {
        return res.status(500).json({ success: false, error: error.message });
    }
}