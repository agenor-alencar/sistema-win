import os
import base64

def gerar_jwt_secret(tamanho_em_bytes=32):
    """
    Gera uma chave secreta forte e aleatória para JWT.
    O valor é gerado com bytes aleatórios e codificado em Base64.
    O tamanho padrão de 32 bytes é ideal para segurança.
    """
    chave_em_bytes = os.urandom(tamanho_em_bytes)
    chave_base64 = base64.b64encode(chave_em_bytes).decode('utf-8')
    return chave_base64

if __name__ == "__main__":
    secret_gerado = gerar_jwt_secret()
    print(f"Sua chave JWT gerada é: {secret_gerado}")