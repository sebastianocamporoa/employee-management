# Employee Management System - Backend

## Descripción del proyecto
El Employee Management System - Backend es la parte del sistema de gestión de empleados encargada de la lógica del negocio y la comunicación con las APIs REST. Proporciona los servicios necesarios para obtener y procesar la información de los empleados, así como calcular sus salarios anuales. Este componente está diseñado como una API REST que puede ser consumida por el frontend o cualquier otra aplicación cliente.

## Características principales
- Consumo de APIs REST para obtener información de empleados.
- Cálculo de salarios anuales de los empleados.
- Implementación de una API REST para exponer los servicios de gestión de empleados.

## Tecnologías utilizadas
- Java 17
- Spring Boot (Framework MVC)
- RESTful APIs

## Configuración y ejecución del proyecto
1. Clona este repositorio en tu máquina local.
2. Abre el proyecto en tu IDE preferido (recomendado: IntelliJ IDEA, Eclipse, Netbeans).
3. Configura cualquier dependencia o configuración necesaria para ejecutar la aplicación.
4. Compila y ejecuta la aplicación.

## Uso de la API
La API del Employee Management System - Backend proporciona los siguientes puntos de entrada:

- **GET /api/employees**: Obtiene la lista de todos los empleados.
- **GET /api/employee/{id}**: Obtiene la información de un empleado específico por su ID.

La API responde en formato JSON y devuelve los datos solicitados, incluido el cálculo del salario anual para cada empleado.

## Pruebas unitarias
El componente de negocio cuenta con pruebas unitarias implementadas utilizando el framework JUnit 5. Estas pruebas garantizan el correcto funcionamiento del cálculo del salario anual y la lógica de la aplicación.
