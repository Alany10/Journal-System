// src/context/UserContext.js
import React, { createContext, useState, useContext } from 'react';

// Skapa UserContext
const UserContext = createContext();

// Skapa en custom hook för att använda UserContext
export const useUser = () => {
    return useContext(UserContext);
};

// Skapa en UserProvider för att hantera användardata
export const UserProvider = ({ children }) => {
    const [user, setUser] = useState(null); // Håll användardata i state

    return (
        <UserContext.Provider value={{ user, setUser }}>
            {children}
        </UserContext.Provider>
    );
};
