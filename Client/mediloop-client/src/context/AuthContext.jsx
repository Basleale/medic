import { createContext, useContext, useState } from "react";

const AuthContext = createContext(); 

export function AuthProvider({ children }) {
  // Hardcode a mock user so you are ALWAYS "logged in"
  const [user, setUser] = useState({
    id: "mock-id-123",
    firstName: "Jane",
    lastName: "Doe",
    role: "Patient", // NOTE: Change this to "Doctor" or "Admin" to view the other dashboards!
    email: "patient@test.com",
    phoneNumber: "+251 911 000 000",
    gender: "Female",
    bloodType: "O+",
    age: 28,
    height: 165,
    weight: 60,
    allergies: ["Penicillin"],
    emergencyContact: {
      name: "John Doe",
      relationship: "Brother",
      phone: "+251 911 111 111"
    }
  });

  // Set loading to false immediately to bypass loading screens
  const [loading, setLoading] = useState(false);

  // Mock logout function so the button doesn't crash the app
  const logout = () => {
    console.log("Logout clicked - Mock user active. Returning to /login");
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, setUser, loading, logout }}> 
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() { 
  return useContext(AuthContext);
}