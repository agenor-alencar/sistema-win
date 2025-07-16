import React, { createContext, useContext, useState, useEffect } from "react";

interface User {
  id: string;
  name: string;
  email: string;
  phone: string;
  document: string;
  type: "customer" | "merchant";
}

interface AuthState {
  user: User | null;
  isLoading: boolean;
  isAuthenticated: boolean;
}

interface AuthContextType {
  user: User | null;
  isLoading: boolean;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<boolean>;
  register: (userData: {
    name: string;
    email: string;
    password: string;
    document: string;
    phone?: string;
  }) => Promise<boolean>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [state, setState] = useState<AuthState>({
    user: null,
    isLoading: true,
    isAuthenticated: false,
  });

  // Load user from localStorage on mount
  useEffect(() => {
    const savedUser = localStorage.getItem("win-user");
    if (savedUser) {
      try {
        const user = JSON.parse(savedUser);
        setState({
          user,
          isLoading: false,
          isAuthenticated: true,
        });
      } catch (error) {
        console.error("Error loading user from localStorage:", error);
        setState({
          user: null,
          isLoading: false,
          isAuthenticated: false,
        });
      }
    } else {
      setState({
        user: null,
        isLoading: false,
        isAuthenticated: false,
      });
    }
  }, []);

  const login = async (email: string, password: string): Promise<boolean> => {
    setState((prev) => ({ ...prev, isLoading: true }));

    // Simulate API call
    await new Promise((resolve) => setTimeout(resolve, 1000));

    // Mock user data - in real app this would come from API
    const mockUser: User = {
      id: "user-123",
      name: "João Silva",
      email: email,
      phone: "(11) 99999-9999",
      document: "123.456.789-00",
      type: email.includes("loja") ? "merchant" : "customer",
    };

    // Simulate successful login
    if (email && password) {
      localStorage.setItem("win-user", JSON.stringify(mockUser));
      setState({
        user: mockUser,
        isLoading: false,
        isAuthenticated: true,
      });
      return true;
    } else {
      setState((prev) => ({ ...prev, isLoading: false }));
      return false;
    }
  };

  const register = async (userData: {
    name: string;
    email: string;
    password: string;
    document: string;
    phone?: string;
  }): Promise<boolean> => {
    setState((prev) => ({ ...prev, isLoading: true }));

    // Simulate API call
    await new Promise((resolve) => setTimeout(resolve, 1000));

    const newUser: User = {
      id: `user-${Date.now()}`,
      name: userData.name,
      email: userData.email,
      phone: userData.phone || "",
      document: userData.document,
      type: "customer",
    };

    localStorage.setItem("win-user", JSON.stringify(newUser));
    setState({
      user: newUser,
      isLoading: false,
      isAuthenticated: true,
    });

    return true;
  };

  const logout = () => {
    localStorage.removeItem("win-user");
    setState({
      user: null,
      isLoading: false,
      isAuthenticated: false,
    });
  };

  return (
    <AuthContext.Provider
      value={{
        user: state.user,
        isLoading: state.isLoading,
        isAuthenticated: state.isAuthenticated,
        login,
        register,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
