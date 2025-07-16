import React from "react";
import "./global.css";

import { NotificationToaster } from "@/components/ui/NotificationToaster";
import { createRoot } from "react-dom/client";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { CartProvider } from "./contexts/CartContext";
import { AuthProvider } from "./contexts/AuthContext";
import { SearchProvider } from "./contexts/SearchContext";
import { NotificationProvider } from "./contexts/NotificationContext";
// Páginas compartilhadas
import Index from "./pages/shared/Index";
import Login from "./pages/shared/Login";
import Categories from "./pages/shared/Categories";
import Product from "./pages/shared/Product";
import SearchResults from "./pages/shared/SearchResults";
import Help from "./pages/shared/Help";
import Terms from "./pages/shared/Terms";
import Privacy from "./pages/shared/Privacy";
import BecomeASeller from "./pages/shared/BecomeASeller";
import TrackOrder from "./pages/shared/TrackOrder";
import Deals from "./pages/shared/Deals";
import NewArrivals from "./pages/shared/NewArrivals";
import NotFound from "./pages/shared/NotFound";

// Páginas do cliente
import Cart from "./pages/customer/CartSimple";
import Checkout from "./pages/customer/Checkout";
import OrderSuccess from "./pages/customer/OrderSuccess";
import OrderDetails from "./pages/customer/OrderDetails";
import Orders from "./pages/customer/Orders";
import Profile from "./pages/customer/Profile";
import Favorites from "./pages/customer/Favorites";
import Settings from "./pages/customer/Settings";
import Addresses from "./pages/customer/Addresses";
import PaymentMethods from "./pages/customer/PaymentMethods";
import Notifications from "./pages/customer/Notifications";
import Reviews from "./pages/customer/Reviews";
import Returns from "./pages/customer/Returns";

// Páginas da loja/merchant
import MerchantDashboard from "./pages/merchant/MerchantDashboardNew";
import MerchantProducts from "./pages/merchant/MerchantProducts";
import MerchantOrders from "./pages/merchant/MerchantOrders";
import MerchantProfile from "./pages/merchant/MerchantProfile";
import MerchantAuth from "./pages/merchant/MerchantAuth";
import MerchantReturns from "./pages/merchant/MerchantReturns";
import MerchantProductForm from "./pages/merchant/MerchantProductForm";
import MerchantFinancial from "./pages/merchant/MerchantFinancial";
import MerchantSettings from "./pages/merchant/MerchantSettings";

// Páginas do motorista/driver
import DriverLogin from "./pages/driver/DriverLogin";
import DriverRegister from "./pages/driver/DriverRegister";
import DriverPendingApproval from "./pages/driver/DriverPendingApproval";
import DriverDashboard from "./pages/driver/DriverDashboard";
import DriverActiveDelivery from "./pages/driver/DriverActiveDelivery";
import DriverHistory from "./pages/driver/DriverHistory";
import DriverProfile from "./pages/driver/DriverProfileUpdated";
import DriverBanking from "./pages/driver/DriverBanking";

// Páginas do admin
import AdminIndex from "./pages/admin/AdminIndex";
import AdminDashboard from "./pages/admin/AdminDashboard";
import AdminUsers from "./pages/admin/AdminUsers";
import AdminStores from "./pages/admin/AdminStores";
import AdminDrivers from "./pages/admin/AdminDrivers";
import AdminCategories from "./pages/admin/AdminCategories";
import AdminProducts from "./pages/admin/AdminProducts";
import AdminOrders from "./pages/admin/AdminOrders";
import AdminDeliveries from "./pages/admin/AdminDeliveries";
import AdminFinances from "./pages/admin/AdminFinances";
import AdminSettings from "./pages/admin/AdminSettings";
import AdminProfile from "./pages/admin/AdminProfile";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <AuthProvider>
      <CartProvider>
        <SearchProvider>
          <NotificationProvider>
            <TooltipProvider>
              <NotificationToaster />
              <Sonner />
              <BrowserRouter>
                <Routes>
                  <Route path="/" element={<Index />} />
                  <Route path="/login" element={<Login />} />
                  <Route path="/categories" element={<Categories />} />
                  <Route path="/category/:category" element={<Categories />} />
                  <Route path="/search" element={<SearchResults />} />
                  <Route path="/product/:id" element={<Product />} />
                  <Route path="/cart" element={<Cart />} />
                  <Route path="/checkout" element={<Checkout />} />
                  <Route
                    path="/order-success/:orderId"
                    element={<OrderSuccess />}
                  />
                  <Route path="/order/:orderId" element={<OrderDetails />} />
                  <Route path="/orders" element={<Orders />} />
                  <Route path="/profile" element={<Profile />} />
                  <Route path="/favorites" element={<Favorites />} />
                  <Route path="/settings" element={<Settings />} />
                  <Route path="/help" element={<Help />} />
                  <Route path="/addresses" element={<Addresses />} />
                  <Route path="/payment-methods" element={<PaymentMethods />} />
                  <Route path="/notifications" element={<Notifications />} />
                  <Route path="/reviews" element={<Reviews />} />
                  <Route path="/returns" element={<Returns />} />
                  <Route path="/terms" element={<Terms />} />
                  <Route path="/privacy" element={<Privacy />} />
                  <Route path="/sell" element={<BecomeASeller />} />
                  <Route path="/track" element={<TrackOrder />} />
                  <Route path="/deals" element={<Deals />} />
                  <Route path="/new-arrivals" element={<NewArrivals />} />
                  <Route path="/merchant/login" element={<MerchantAuth />} />
                  <Route path="/merchant/auth" element={<MerchantAuth />} />
                  <Route
                    path="/merchant/dashboard"
                    element={<MerchantDashboard />}
                  />
                  <Route
                    path="/merchant/products"
                    element={<MerchantProducts />}
                  />
                  <Route
                    path="/merchant/products/new"
                    element={<MerchantProductForm />}
                  />
                  <Route
                    path="/merchant/products/edit/:id"
                    element={<MerchantProductForm />}
                  />
                  <Route path="/merchant/orders" element={<MerchantOrders />} />
                  <Route
                    path="/merchant/returns"
                    element={<MerchantReturns />}
                  />
                  <Route
                    path="/merchant/financial"
                    element={<MerchantFinancial />}
                  />
                  <Route
                    path="/merchant/settings"
                    element={<MerchantSettings />}
                  />
                  <Route
                    path="/merchant/profile"
                    element={<MerchantProfile />}
                  />

                  {/* Driver Routes */}
                  <Route path="/driver/login" element={<DriverLogin />} />
                  <Route path="/driver/register" element={<DriverRegister />} />
                  <Route
                    path="/driver/pending-approval"
                    element={<DriverPendingApproval />}
                  />
                  <Route
                    path="/driver/dashboard"
                    element={<DriverDashboard />}
                  />
                  <Route
                    path="/driver/delivery/:orderId"
                    element={<DriverActiveDelivery />}
                  />
                  <Route
                    path="/driver/active"
                    element={<DriverActiveDelivery />}
                  />
                  <Route path="/driver/history" element={<DriverHistory />} />
                  <Route path="/driver/banking" element={<DriverBanking />} />
                  <Route path="/driver/profile" element={<DriverProfile />} />

                  {/* Admin Routes */}
                  <Route path="/admin" element={<AdminIndex />} />
                  <Route path="/admin/dashboard" element={<AdminDashboard />} />
                  <Route path="/admin/users" element={<AdminUsers />} />
                  <Route path="/admin/stores" element={<AdminStores />} />
                  <Route path="/admin/drivers" element={<AdminDrivers />} />
                  <Route
                    path="/admin/categories"
                    element={<AdminCategories />}
                  />
                  <Route path="/admin/products" element={<AdminProducts />} />
                  <Route path="/admin/orders" element={<AdminOrders />} />
                  <Route
                    path="/admin/deliveries"
                    element={<AdminDeliveries />}
                  />
                  <Route path="/admin/finances" element={<AdminFinances />} />
                  <Route path="/admin/settings" element={<AdminSettings />} />
                  <Route path="/admin/profile" element={<AdminProfile />} />

                  {/* ADD ALL CUSTOM ROUTES ABOVE THE CATCH-ALL "*" ROUTE */}
                  <Route path="*" element={<NotFound />} />
                </Routes>
              </BrowserRouter>
            </TooltipProvider>
          </NotificationProvider>
        </SearchProvider>
      </CartProvider>
    </AuthProvider>
  </QueryClientProvider>
);

createRoot(document.getElementById("root")!).render(<App />);
