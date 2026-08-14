const SECRET = "EvaluacionPlataformas2026";

export async function cifrarAES(texto: string): Promise<string> {

    const encoder = new TextEncoder();


    const secretBytes = encoder.encode(SECRET);

    const hash = await crypto.subtle.digest(
        "SHA-256",
        secretBytes
    );

    const key = await crypto.subtle.importKey(
        "raw",
        hash,
        {
            name: "AES-GCM"
        },
        false,
        ["encrypt"]
    );

    const iv = crypto.getRandomValues(
        new Uint8Array(12)
    );

    const textoBytes = encoder.encode(texto);

    const cifrado = await crypto.subtle.encrypt(
        {
            name: "AES-GCM",
            iv: iv
        },
        key,
        textoBytes
    );

    const cifradoBytes = new Uint8Array(cifrado);

    const combinado = new Uint8Array(
        iv.length + cifradoBytes.length
    );

    combinado.set(iv, 0);
    combinado.set(cifradoBytes, iv.length);

    let cadena = "";

    combinado.forEach(byte => {
        cadena += String.fromCharCode(byte);
    });

    return btoa(cadena);
}