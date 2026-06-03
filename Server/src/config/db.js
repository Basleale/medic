// Server/src/config/db.js
import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

const connectDB = async () => {
    try {
        await prisma.$connect();
        console.log("Neon PostgreSQL connected successfully!");
    } catch (error) {
        console.error("Database Connection Failed!", error.message);
        process.exit(1);
    }
};

export { prisma };
export default connectDB;