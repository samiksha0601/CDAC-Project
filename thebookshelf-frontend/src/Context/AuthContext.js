

import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [userId, setUserId,setUser] = useState(null);

  useEffect(() => {
    // Fetch or set the user information (e.g., from localStorage)
    const loggedInUser = JSON.parse(localStorage.getItem('user'));
    setUser(loggedInUser);
  }, []);

  return (
    <AuthContext.Provider value={{ userId, setUserId }}>
      {children}
    </AuthContext.Provider>
  );
};
