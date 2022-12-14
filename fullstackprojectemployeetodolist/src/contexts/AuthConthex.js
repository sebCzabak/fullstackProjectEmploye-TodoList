import React, { useContext, useState, useEffect } from "react";
import { auth } from "../firebase";

const AuthConthex = React.createContext();

export function useAuth() {
  return useContext(AuthConthex);
}

export function AuthProvider({ children }) {
  const [currentUser, setCurrentUser] = useState();

  function signup(email, password) {
    return auth.createUserWithEmailAndPassword(email, password);
  }
  useEffect(() => {
    const unsubscribe = auth.onAuthStateChanged((user) => {
      setCurrentUser(user);
    });
    return unsubscribe;
  }, []);

  const value = {
    currentUser,
    signup,
  };
  return <AuthConthex.Consumer value={value}>{children}</AuthConthex.Consumer>;
}
