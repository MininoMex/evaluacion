import { useState } from "react";
import { cifrarAES } from "./crypto";

function App() {

    const [logueado, setLogueado] = useState(false);

    const [usuario, setUsuario] = useState("");
    const [password, setPassword] = useState("");

    const [operacion, setOperacion] = useState("");
    const [importe, setImporte] = useState("");
    const [cliente, setCliente] = useState("");
    const [secreto, setSecreto] = useState("");


    const iniciarSesion = async () => {

        try {

            const response = await fetch(
                "http://localhost:8080/api/login",
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify({
                        usuario,
                        password
                    })
                }
            );


            const data = await response.json();


            if (data.correcto) {

                setLogueado(true);

            } else {

                alert(data.mensaje);
            }

        } catch (error) {

            alert("Error al conectar con el servidor");
        }
    };


    const enviarOperacion = async () => {

        try {

            const secretoCifrado =
                await cifrarAES(secreto);


            const response = await fetch(
                "http://localhost:8080/api/operaciones",
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify({

                        operacion,

                        importe: Number(importe),

                        cliente,

                        secreto: secretoCifrado
                    })
                }
            );


            const data = await response.json();

            if (!response.ok) {

                alert(
                    JSON.stringify(
                        data,
                        null,
                        2
                    )
                );

                return;
            }

            alert(
                "OPERACIÓN EXITOSA\n\n" +
                "ID: " + data.id + "\n" +
                "Estatus: " + data.estatus + "\n" +
                "Referencia: " + data.referencia + "\n" +
                "Operación: " + data.operacion
            );

            setOperacion("");
            setImporte("");
            setCliente("");
            setSecreto("");

        } catch (error) {

            alert(
                "Error al procesar la operación"
            );
        }
    };

    if (!logueado) {

        return (
            <div className="contenedor">

                <h1>Login</h1>

                <input
                    type="text"
                    placeholder="Usuario"
                    value={usuario}
                    onChange={(e) =>
                        setUsuario(e.target.value)
                    }
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) =>
                        setPassword(e.target.value)
                    }
                />

                <button
                    onClick={iniciarSesion}
                >
                    Iniciar sesión
                </button>

            </div>
        );
    }

    return (
        <div className="contenedor">

            <h1>
                Registrar operación
            </h1>

            <input
                type="text"
                placeholder="Operación"
                value={operacion}
                onChange={(e) =>
                    setOperacion(e.target.value)
                }
            />

            <input
                type="number"
                step="0.01"
                placeholder="Importe"
                value={importe}
                onChange={(e) =>
                    setImporte(e.target.value)
                }
            />

            <input
                type="text"
                placeholder="Cliente"
                value={cliente}
                onChange={(e) =>
                    setCliente(e.target.value)
                }
            />

            <input
                type="text"
                placeholder="Secreto"
                value={secreto}
                onChange={(e) =>
                    setSecreto(e.target.value)
                }
            />

            <button
                onClick={enviarOperacion}
            >
                Enviar operación
            </button>

        </div>
    );
}

export default App;