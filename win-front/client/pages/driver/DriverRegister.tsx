import { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Checkbox } from "@/components/ui/checkbox";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import {
  Truck,
  User,
  Car,
  Camera,
  CheckCircle,
  ArrowLeft,
  ArrowRight,
  Upload,
  FileText,
  Shield,
  Clock,
} from "lucide-react";
import { useNotification } from "../../contexts/NotificationContext";

interface DriverData {
  // Dados Pessoais
  fullName: string;
  document: string;
  phone: string;
  email: string;
  birthDate: string;
  address: string;
  city: string;
  state: string;
  zipCode: string;

  // Veículo
  vehicleType: string;
  licensePlate: string;
  vehicleModel: string;
  vehicleYear: string;
  vehicleColor: string;

  // Documentação
  cnhFront: File | null;
  cnhBack: File | null;
  profilePhoto: File | null;
  selfieVerification: File | null;

  // Termos
  acceptedTerms: boolean;
  password: string;
  confirmPassword: string;
}

export default function DriverRegister() {
  const [currentStep, setCurrentStep] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const { success, error: notifyError } = useNotification();

  const [driverData, setDriverData] = useState<DriverData>({
    fullName: "",
    document: "",
    phone: "",
    email: "",
    birthDate: "",
    address: "",
    city: "",
    state: "",
    zipCode: "",
    vehicleType: "",
    licensePlate: "",
    vehicleModel: "",
    vehicleYear: "",
    vehicleColor: "",
    cnhFront: null,
    cnhBack: null,
    profilePhoto: null,
    selfieVerification: null,
    acceptedTerms: false,
    password: "",
    confirmPassword: "",
  });

  const updateDriverData = (field: keyof DriverData, value: any) => {
    setDriverData((prev) => ({ ...prev, [field]: value }));
  };

  const nextStep = () => {
    if (validateCurrentStep()) {
      setCurrentStep((prev) => Math.min(prev + 1, 4));
    }
  };

  const prevStep = () => {
    setCurrentStep((prev) => Math.max(prev - 1, 1));
  };

  const validateCurrentStep = () => {
    switch (currentStep) {
      case 1:
        if (!driverData.fullName || !driverData.document || !driverData.email) {
          notifyError(
            "Campos obrigatórios",
            "Preencha todos os campos marcados com *",
          );
          return false;
        }
        break;
      case 2:
        if (!driverData.vehicleType || !driverData.licensePlate) {
          notifyError("Dados do veículo", "Preencha os dados do veículo");
          return false;
        }
        break;
      case 3:
        if (!driverData.cnhFront || !driverData.profilePhoto) {
          notifyError(
            "Documentação obrigatória",
            "Envie pelo menos a CNH e foto de perfil",
          );
          return false;
        }
        break;
    }
    return true;
  };

  const handleSubmit = async () => {
    if (!driverData.acceptedTerms) {
      notifyError("Termos obrigatórios", "Aceite os termos para continuar");
      return;
    }

    if (driverData.password !== driverData.confirmPassword) {
      notifyError(
        "Senhas não coincidem",
        "Digite a mesma senha nos dois campos",
      );
      return;
    }

    setIsLoading(true);
    setTimeout(() => {
      setIsLoading(false);
      success("Cadastro enviado!", "Aguarde análise dos documentos");
      setTimeout(() => {
        window.location.href = "/driver/pending-approval";
      }, 2000);
    }, 3000);
  };

  const handleFileUpload = (field: keyof DriverData, file: File) => {
    updateDriverData(field, file);
    success("Arquivo enviado!", `${file.name} foi adicionado`);
  };

  const steps = [
    { number: 1, title: "Dados Pessoais", icon: User },
    { number: 2, title: "Veículo", icon: Car },
    { number: 3, title: "Documentação", icon: FileText },
    { number: 4, title: "Finalizar", icon: CheckCircle },
  ];

  return (
    <div
      className="min-h-screen px-4 py-8"
      style={{
        backgroundColor: "#F8FFFE",
        fontFamily: "Inter, sans-serif",
        background: "linear-gradient(135deg, #F8FFFE 0%, #F0F9FF 100%)",
      }}
    >
      <div className="max-w-2xl mx-auto">
        {/* Header */}
        <div className="text-center mb-8">
          <div className="flex items-center justify-center mb-4">
            <Link to="/driver/login" className="mr-4">
              <Button variant="ghost" size="icon">
                <ArrowLeft className="h-5 w-5" />
              </Button>
            </Link>
            <div
              className="p-3 rounded-full mr-3"
              style={{
                background: "linear-gradient(135deg, #3DBEAB 0%, #2D9CDB 100%)",
              }}
            >
              <Truck className="h-6 w-6 text-white" />
            </div>
            <div>
              <h1
                className="text-3xl font-bold"
                style={{
                  background:
                    "linear-gradient(135deg, #3DBEAB 0%, #2D9CDB 100%)",
                  WebkitBackgroundClip: "text",
                  WebkitTextFillColor: "transparent",
                }}
              >
                Seja um Motorista WIN
              </h1>
            </div>
          </div>
          <p style={{ color: "#666666", fontSize: "16px" }}>
            Cadastre-se e comece a fazer entregas hoje mesmo
          </p>
        </div>

        {/* Progress Steps */}
        <div className="mb-8">
          <div className="flex items-center justify-between">
            {steps.map((step, index) => {
              const StepIcon = step.icon;
              const isActive = currentStep === step.number;
              const isCompleted = currentStep > step.number;

              return (
                <div key={step.number} className="flex items-center">
                  <div
                    className={`w-12 h-12 rounded-full flex items-center justify-center ${
                      isCompleted
                        ? "bg-green-500"
                        : isActive
                          ? "bg-gradient-to-r from-[#3DBEAB] to-[#2D9CDB]"
                          : "bg-gray-200"
                    }`}
                  >
                    {isCompleted ? (
                      <CheckCircle className="h-6 w-6 text-white" />
                    ) : (
                      <StepIcon
                        className={`h-6 w-6 ${isActive ? "text-white" : "text-gray-500"}`}
                      />
                    )}
                  </div>
                  <div className="ml-3 hidden md:block">
                    <p
                      className={`text-sm font-medium ${
                        isActive ? "text-[#3DBEAB]" : "text-gray-500"
                      }`}
                    >
                      Etapa {step.number}
                    </p>
                    <p
                      className={`text-xs ${isActive ? "text-[#2D9CDB]" : "text-gray-400"}`}
                    >
                      {step.title}
                    </p>
                  </div>
                  {index < steps.length - 1 && (
                    <div
                      className={`flex-1 h-1 mx-4 ${
                        isCompleted ? "bg-green-500" : "bg-gray-200"
                      }`}
                    />
                  )}
                </div>
              );
            })}
          </div>
        </div>

        {/* Step Content */}
        <Card
          className="shadow-xl"
          style={{ borderRadius: "16px", border: "1px solid #E1F5FE" }}
        >
          <CardHeader>
            <CardTitle style={{ fontSize: "20px", color: "#333333" }}>
              {steps[currentStep - 1].title}
            </CardTitle>
          </CardHeader>
          <CardContent className="space-y-6">
            {/* Step 1: Dados Pessoais */}
            {currentStep === 1 && (
              <div className="space-y-4">
                <div>
                  <Label style={{ fontSize: "16px", color: "#333333" }}>
                    Nome Completo *
                  </Label>
                  <Input
                    placeholder="Seu nome completo"
                    value={driverData.fullName}
                    onChange={(e) =>
                      updateDriverData("fullName", e.target.value)
                    }
                    className="mt-2 h-12"
                    style={{ borderRadius: "12px", fontSize: "16px" }}
                  />
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <Label style={{ fontSize: "16px", color: "#333333" }}>
                      CPF *
                    </Label>
                    <Input
                      placeholder="000.000.000-00"
                      value={driverData.document}
                      onChange={(e) =>
                        updateDriverData("document", e.target.value)
                      }
                      className="mt-2 h-12"
                      style={{ borderRadius: "12px", fontSize: "16px" }}
                    />
                  </div>
                  <div>
                    <Label style={{ fontSize: "16px", color: "#333333" }}>
                      Data de Nascimento *
                    </Label>
                    <Input
                      type="date"
                      value={driverData.birthDate}
                      onChange={(e) =>
                        updateDriverData("birthDate", e.target.value)
                      }
                      className="mt-2 h-12"
                      style={{ borderRadius: "12px", fontSize: "16px" }}
                    />
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <Label style={{ fontSize: "16px", color: "#333333" }}>
                      Telefone *
                    </Label>
                    <Input
                      placeholder="(11) 99999-9999"
                      value={driverData.phone}
                      onChange={(e) =>
                        updateDriverData("phone", e.target.value)
                      }
                      className="mt-2 h-12"
                      style={{ borderRadius: "12px", fontSize: "16px" }}
                    />
                  </div>
                  <div>
                    <Label style={{ fontSize: "16px", color: "#333333" }}>
                      E-mail *
                    </Label>
                    <Input
                      type="email"
                      placeholder="seu@email.com"
                      value={driverData.email}
                      onChange={(e) =>
                        updateDriverData("email", e.target.value)
                      }
                      className="mt-2 h-12"
                      style={{ borderRadius: "12px", fontSize: "16px" }}
                    />
                  </div>
                </div>

                <div>
                  <Label style={{ fontSize: "16px", color: "#333333" }}>
                    Endereço Completo *
                  </Label>
                  <Textarea
                    placeholder="Rua, número, bairro, cidade - UF"
                    value={driverData.address}
                    onChange={(e) =>
                      updateDriverData("address", e.target.value)
                    }
                    className="mt-2"
                    style={{ borderRadius: "12px", fontSize: "16px" }}
                    rows={3}
                  />
                </div>
              </div>
            )}

            {/* Step 2: Veículo */}
            {currentStep === 2 && (
              <div className="space-y-4">
                <div>
                  <Label style={{ fontSize: "16px", color: "#333333" }}>
                    Tipo de Veículo *
                  </Label>
                  <Select
                    value={driverData.vehicleType}
                    onValueChange={(value) =>
                      updateDriverData("vehicleType", value)
                    }
                  >
                    <SelectTrigger
                      className="mt-2 h-12"
                      style={{ borderRadius: "12px" }}
                    >
                      <SelectValue placeholder="Selecione o tipo de veículo" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="moto">Moto</SelectItem>
                      <SelectItem value="carro">Carro</SelectItem>
                      <SelectItem value="van">Van</SelectItem>
                      <SelectItem value="caminhao">Caminhão</SelectItem>
                      <SelectItem value="bicicleta">Bicicleta</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <Label style={{ fontSize: "16px", color: "#333333" }}>
                      Placa *
                    </Label>
                    <Input
                      placeholder="ABC-1234"
                      value={driverData.licensePlate}
                      onChange={(e) =>
                        updateDriverData("licensePlate", e.target.value)
                      }
                      className="mt-2 h-12"
                      style={{ borderRadius: "12px", fontSize: "16px" }}
                    />
                  </div>
                  <div>
                    <Label style={{ fontSize: "16px", color: "#333333" }}>
                      Ano *
                    </Label>
                    <Input
                      placeholder="2020"
                      value={driverData.vehicleYear}
                      onChange={(e) =>
                        updateDriverData("vehicleYear", e.target.value)
                      }
                      className="mt-2 h-12"
                      style={{ borderRadius: "12px", fontSize: "16px" }}
                    />
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <Label style={{ fontSize: "16px", color: "#333333" }}>
                      Modelo *
                    </Label>
                    <Input
                      placeholder="Honda CG 160"
                      value={driverData.vehicleModel}
                      onChange={(e) =>
                        updateDriverData("vehicleModel", e.target.value)
                      }
                      className="mt-2 h-12"
                      style={{ borderRadius: "12px", fontSize: "16px" }}
                    />
                  </div>
                  <div>
                    <Label style={{ fontSize: "16px", color: "#333333" }}>
                      Cor *
                    </Label>
                    <Input
                      placeholder="Vermelha"
                      value={driverData.vehicleColor}
                      onChange={(e) =>
                        updateDriverData("vehicleColor", e.target.value)
                      }
                      className="mt-2 h-12"
                      style={{ borderRadius: "12px", fontSize: "16px" }}
                    />
                  </div>
                </div>
              </div>
            )}

            {/* Step 3: Documentação */}
            {currentStep === 3 && (
              <div className="space-y-6">
                <div
                  className="p-4 rounded-lg"
                  style={{ backgroundColor: "#E1F5FE" }}
                >
                  <div className="flex items-center">
                    <Shield
                      className="h-5 w-5 mr-2"
                      style={{ color: "#2D9CDB" }}
                    />
                    <p style={{ fontSize: "14px", color: "#1565C0" }}>
                      Seus documentos são protegidos e usados apenas para
                      verificação
                    </p>
                  </div>
                </div>

                {/* CNH Upload */}
                <div className="space-y-4">
                  <Label style={{ fontSize: "16px", color: "#333333" }}>
                    CNH (Frente) *
                  </Label>
                  <div
                    className="border-2 border-dashed rounded-lg p-6 text-center cursor-pointer hover:bg-gray-50"
                    style={{ borderColor: "#E1F5FE", borderRadius: "12px" }}
                    onClick={() =>
                      document.getElementById("cnh-front")?.click()
                    }
                  >
                    <Upload
                      className="h-8 w-8 mx-auto mb-2"
                      style={{ color: "#6B7280" }}
                    />
                    <p style={{ fontSize: "14px", color: "#333333" }}>
                      {driverData.cnhFront
                        ? driverData.cnhFront.name
                        : "Clique para enviar CNH (frente)"}
                    </p>
                    <p style={{ fontSize: "12px", color: "#666666" }}>
                      PNG, JPG até 5MB
                    </p>
                  </div>
                  <input
                    id="cnh-front"
                    type="file"
                    accept="image/*"
                    className="hidden"
                    onChange={(e) => {
                      const file = e.target.files?.[0];
                      if (file) handleFileUpload("cnhFront", file);
                    }}
                  />
                </div>

                <div className="space-y-4">
                  <Label style={{ fontSize: "16px", color: "#333333" }}>
                    CNH (Verso)
                  </Label>
                  <div
                    className="border-2 border-dashed rounded-lg p-6 text-center cursor-pointer hover:bg-gray-50"
                    style={{ borderColor: "#E1F5FE", borderRadius: "12px" }}
                    onClick={() => document.getElementById("cnh-back")?.click()}
                  >
                    <Upload
                      className="h-8 w-8 mx-auto mb-2"
                      style={{ color: "#6B7280" }}
                    />
                    <p style={{ fontSize: "14px", color: "#333333" }}>
                      {driverData.cnhBack
                        ? driverData.cnhBack.name
                        : "Clique para enviar CNH (verso)"}
                    </p>
                    <p style={{ fontSize: "12px", color: "#666666" }}>
                      PNG, JPG até 5MB
                    </p>
                  </div>
                  <input
                    id="cnh-back"
                    type="file"
                    accept="image/*"
                    className="hidden"
                    onChange={(e) => {
                      const file = e.target.files?.[0];
                      if (file) handleFileUpload("cnhBack", file);
                    }}
                  />
                </div>

                {/* Profile Photo */}
                <div className="space-y-4">
                  <Label style={{ fontSize: "16px", color: "#333333" }}>
                    Foto de Perfil *
                  </Label>
                  <div
                    className="border-2 border-dashed rounded-lg p-6 text-center cursor-pointer hover:bg-gray-50"
                    style={{ borderColor: "#E1F5FE", borderRadius: "12px" }}
                    onClick={() =>
                      document.getElementById("profile-photo")?.click()
                    }
                  >
                    <Camera
                      className="h-8 w-8 mx-auto mb-2"
                      style={{ color: "#6B7280" }}
                    />
                    <p style={{ fontSize: "14px", color: "#333333" }}>
                      {driverData.profilePhoto
                        ? driverData.profilePhoto.name
                        : "Foto de perfil (fundo claro)"}
                    </p>
                    <p style={{ fontSize: "12px", color: "#666666" }}>
                      Foto nítida com fundo claro
                    </p>
                  </div>
                  <input
                    id="profile-photo"
                    type="file"
                    accept="image/*"
                    className="hidden"
                    onChange={(e) => {
                      const file = e.target.files?.[0];
                      if (file) handleFileUpload("profilePhoto", file);
                    }}
                  />
                </div>

                {/* Selfie Verification */}
                <div className="space-y-4">
                  <Label style={{ fontSize: "16px", color: "#333333" }}>
                    Selfie de Verificação
                  </Label>
                  <div
                    className="border-2 border-dashed rounded-lg p-6 text-center cursor-pointer hover:bg-gray-50"
                    style={{ borderColor: "#E1F5FE", borderRadius: "12px" }}
                    onClick={() =>
                      document.getElementById("selfie-verification")?.click()
                    }
                  >
                    <User
                      className="h-8 w-8 mx-auto mb-2"
                      style={{ color: "#6B7280" }}
                    />
                    <p style={{ fontSize: "14px", color: "#333333" }}>
                      {driverData.selfieVerification
                        ? driverData.selfieVerification.name
                        : "Selfie segurando a CNH"}
                    </p>
                    <p style={{ fontSize: "12px", color: "#666666" }}>
                      Para verificação facial
                    </p>
                  </div>
                  <input
                    id="selfie-verification"
                    type="file"
                    accept="image/*"
                    className="hidden"
                    onChange={(e) => {
                      const file = e.target.files?.[0];
                      if (file) handleFileUpload("selfieVerification", file);
                    }}
                  />
                </div>

                <div
                  className="p-4 rounded-lg"
                  style={{ backgroundColor: "#FFF7ED" }}
                >
                  <div className="flex items-start">
                    <Clock
                      className="h-5 w-5 mr-2 flex-shrink-0 mt-0.5"
                      style={{ color: "#F59E0B" }}
                    />
                    <div>
                      <p
                        style={{
                          fontSize: "12px",
                          color: "#92400E",
                          fontWeight: "600",
                        }}
                      >
                        Importante:
                      </p>
                      <p style={{ fontSize: "11px", color: "#92400E" }}>
                        Essas informações serão analisadas pela equipe WIN. O
                        motorista só poderá fazer entregas após aprovação.
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            )}

            {/* Step 4: Finalizar */}
            {currentStep === 4 && (
              <div className="space-y-6">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <Label style={{ fontSize: "16px", color: "#333333" }}>
                      Criar Senha *
                    </Label>
                    <Input
                      type="password"
                      placeholder="Mínimo 6 caracteres"
                      value={driverData.password}
                      onChange={(e) =>
                        updateDriverData("password", e.target.value)
                      }
                      className="mt-2 h-12"
                      style={{ borderRadius: "12px", fontSize: "16px" }}
                    />
                  </div>
                  <div>
                    <Label style={{ fontSize: "16px", color: "#333333" }}>
                      Confirmar Senha *
                    </Label>
                    <Input
                      type="password"
                      placeholder="Digite a senha novamente"
                      value={driverData.confirmPassword}
                      onChange={(e) =>
                        updateDriverData("confirmPassword", e.target.value)
                      }
                      className="mt-2 h-12"
                      style={{ borderRadius: "12px", fontSize: "16px" }}
                    />
                  </div>
                </div>

                <div className="flex items-start space-x-3">
                  <Checkbox
                    id="terms"
                    checked={driverData.acceptedTerms}
                    onCheckedChange={(checked) =>
                      updateDriverData("acceptedTerms", checked)
                    }
                  />
                  <label
                    htmlFor="terms"
                    style={{ fontSize: "14px", color: "#333333" }}
                  >
                    Li e aceito os{" "}
                    <Link
                      to="/terms"
                      style={{ color: "#2D9CDB" }}
                      className="hover:underline"
                    >
                      Termos de Uso
                    </Link>{" "}
                    e{" "}
                    <Link
                      to="/privacy"
                      style={{ color: "#2D9CDB" }}
                      className="hover:underline"
                    >
                      Política de Privacidade
                    </Link>
                  </label>
                </div>

                <Button
                  onClick={handleSubmit}
                  disabled={isLoading}
                  className="w-full h-12 text-white font-medium"
                  style={{
                    background:
                      "linear-gradient(135deg, #3DBEAB 0%, #2D9CDB 100%)",
                    borderRadius: "12px",
                    fontSize: "16px",
                  }}
                >
                  {isLoading ? "Finalizando Cadastro..." : "Finalizar Cadastro"}
                </Button>
              </div>
            )}

            {/* Navigation Buttons */}
            <div className="flex justify-between pt-6">
              {currentStep > 1 && (
                <Button
                  onClick={prevStep}
                  variant="outline"
                  className="flex items-center"
                  style={{ borderRadius: "12px" }}
                >
                  <ArrowLeft className="h-4 w-4 mr-2" />
                  Voltar
                </Button>
              )}

              {currentStep < 4 && (
                <Button
                  onClick={nextStep}
                  className="flex items-center ml-auto"
                  style={{
                    background:
                      "linear-gradient(135deg, #3DBEAB 0%, #2D9CDB 100%)",
                    borderRadius: "12px",
                  }}
                >
                  Próximo
                  <ArrowRight className="h-4 w-4 ml-2" />
                </Button>
              )}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
