# Java

Colección de ejercicios y proyectos en **Java** de mis cursos de programación y estructuras de datos: desde ejercicios básicos con vectores hasta un sistema de gestión con archivos, multiplicación de matrices recursiva y árboles binarios de búsqueda.

## Contenido

### Ejercicios básicos

| Archivo | Qué hace |
|---|---|
| `vaccaciones.java` | Calcula el salario de un empleado a partir del valor de la hora y las horas trabajadas. |
| `vaccaciones2.java` | Lee *n* notas y muestra el promedio, la nota mínima y la máxima. |
| `vaccaciones3.java` | Suma los elementos de un vector ingresados por el usuario. |
| `vaccaciones4.java` | Genera un vector aleatorio y lo separa en dos vectores, uno de pares y otro de impares. |
| `vaccaciones5.java` | Genera un vector aleatorio y lo invierte. |
| `vaccaciones6.java` | Busca un "número especial" dentro de un vector e indica su posición. |
| `grafobipartido.java` | Determina si un grafo con conjuntos de vértices X e Y es bipartido, a partir de sus matrices de adyacencia. |

### Manejo de archivos

| Archivo | Qué hace |
|---|---|
| `archivos.java` | Crea un archivo de texto y escribe diez líneas en él. |
| `archivos2.java` | Registro de clientes en un archivo `.txt` (nombre, cédula y saldo): escribir, leer y buscar por cédula. |

### Sistema de gestión deportiva

`GestionDeportiva.java` es un sistema por consola que guarda la información en archivos de texto:

- **Entrenadores** (`entrenadores.txt`) y **deportistas** (`deportistas.txt`): registro, validación de cédula de 10 dígitos y especialidad (fuerza, velocidad, resistencia, flexibilidad o fisioterapia).
- **Sesiones** (`sesiones.txt`): se programan con un entrenador de la misma especialidad, en una fecha del mes actual, y reciben un código único de 8 dígitos.
- **Límites mensuales:** 4 sesiones por deportista y 15 por entrenador. Los contadores se actualizan al programar o eliminar sesiones.
- Ver y eliminar sesiones futuras, y eliminar entrenadores o deportistas junto con sus sesiones.

### Trabajo final: operaciones con matrices

Dos versiones del mismo problema con matrices cuadradas de `BigInteger` generadas al azar: calcular la transpuesta, el producto de la matriz por su transpuesta y la potencia *l* de ese producto, midiendo el tiempo de ejecución.

| Archivo | Enfoque |
|---|---|
| `TrabajoFinal.java` | Iterativo: ciclos anidados y potencia por multiplicaciones sucesivas. |
| `TrabajoFinalRecursivo.java` | Recursivo: transpuesta y producto recursivos, y potencia por exponenciación rápida (divide y vencerás). |

### Árboles binarios de búsqueda

| Archivo | Qué hace |
|---|---|
| `ConstruirArbolInorden.java` | Construye un árbol binario de búsqueda balanceado a partir de un recorrido inorden y lo imprime en forma de árbol. |
| `ArbolAbb/Proyecto_2_Arbol/` | Proyecto de NetBeans con la clase `Arbol`: inserción, búsqueda, altura, suma, mayor y menor, eliminación (hoja, con un hijo, o con dos hijos usando predecesor o sucesor), verificación de árbol completo y recorridos preorden, inorden y posorden, tanto recursivos como iterativos con pilas. |

### Datos

- `deportistas.txt`, `entrenadores.txt` y `sesiones.txt`: datos de ejemplo para `GestionDeportiva`.
- `peliculaslab2.csv`: películas, horarios, salas y ocupación de puestos para un ejercicio de laboratorio.

## Cómo ejecutarlo

Requisitos: **JDK 8 o superior**.

Cada archivo de la carpeta principal es un programa independiente:

```bash
javac vaccaciones2.java
java vaccaciones2
```

`GestionDeportiva` debe ejecutarse desde esta misma carpeta, para que encuentre los archivos `.txt`:

```bash
javac GestionDeportiva.java
java GestionDeportiva
```

El proyecto del árbol se abre en **Apache NetBeans** (*File → Open Project → `ArbolAbb/Proyecto_2_Arbol`*) y se ejecuta con *Run Project*. También se puede compilar desde la terminal:

```bash
cd ArbolAbb/Proyecto_2_Arbol/src
javac proyecto_2_arbol/*.java
java proyecto_2_arbol.ConstruirArbol
```

## Autor

- **Jacobo Londoño**, [LinkedIn](https://www.linkedin.com/in/jacobo-londo%C3%B1o-baquero-a2b86733a/)
