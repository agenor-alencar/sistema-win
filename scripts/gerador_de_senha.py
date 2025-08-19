import random
import string

def gerar_senha_forte(tamanho=16):
    """
    Gera uma senha forte e aleatória com um tamanho especificado.
    A senha conterá letras maiúsculas, minúsculas, números e símbolos.
    """
    caracteres_minusculos = string.ascii_lowercase
    caracteres_maiusculos = string.ascii_uppercase
    digitos = string.digits
    simbolos = string.punctuation
    todos_caracteres = caracteres_minusculos + caracteres_maiusculos + digitos + simbolos
    senha = [
        random.choice(caracteres_minusculos),
        random.choice(caracteres_maiusculos),
        random.choice(digitos),
        random.choice(simbolos)
    ]

    if tamanho > 4:
        senha += random.choices(todos_caracteres, k=tamanho - 4)
    random.shuffle(senha)
    return "".join(senha)

if __name__ == "__main__":
    senha_gerada = gerar_senha_forte()
    print(f"Sua senha forte gerada é: {senha_gerada}")
