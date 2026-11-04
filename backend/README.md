# Wild Print Backend

**Backend de la plataforma Wild Print**

Backend desarrollado para proporcionar servicios REST destinados a la administración del catálogo, productos, categorías, imágenes y enlaces externos de comercialización de Wild Print.

---

## Información del proyecto

| Propiedad     | Valor                     |
|---------------|---------------------------|
| Proyecto      | Wild Print Backend        |
| Versión       | 0.0.1-SNAPSHOT            |
| Organización  | R'lyeh Software Solutions |
| Desarrollador | Ricardo Cruz              |
| Grupo Maven   | `cl.wildprint`            |
| Artefacto     | `wild-print-backend`      |
| Estado        | Desarrollo inicial        |

## Descripción

Wild Print es una plataforma orientada a la presentación y comercialización de modelos digitales y diseños 3D.

Este backend proporciona la infraestructura necesaria para administrar el catálogo de productos, organizar categorías, gestionar imágenes y mantener enlaces hacia plataformas externas de comercialización.

El proyecto sigue una arquitectura modular monolítica, manteniendo separadas las responsabilidades de persistencia, lógica de negocio, exposición de servicios REST y almacenamiento de archivos.

## Tecnologías

| Tecnología          | Utilización                            |
|---------------------|----------------------------------------|
| Java 25             | Lenguaje de programación               |
| Spring Boot 4.1.2   | Framework principal                    |
| Spring Web MVC      | API REST                               |
| Spring Data JPA     | Persistencia y acceso a datos          |
| Spring Security     | Seguridad y autenticación              |
| Spring Session JDBC | Gestión de sesiones                    |
| PostgreSQL          | Base de datos relacional               |
| Flyway              | Control de versiones del esquema       |
| Hibernate           | ORM                                    |
| Lombok              | Reducción de código repetitivo         |
| Maven               | Gestión de dependencias y construcción |
| Cloudflare R2       | Almacenamiento externo de imágenes     |
| Springdoc OpenAPI   | Documentación de API                   |

## Arquitectura

El proyecto utiliza una arquitectura modular monolítica. Los componentes se organizan por responsabilidad:

```text
cl.wildprint
│
├── config
│   └── Configuraciones de la aplicación
│
├── controller
│   └── Controladores REST
│
├── dto
│   └── Objetos de transferencia de datos
│
├── entity
│   └── Entidades JPA
│
├── mapper
│   └── Transformaciones entre entidades y DTO
│
├── repository
│   └── Repositorios Spring Data JPA
│
├── service
│   ├── Interfaces de servicios
│   └── impl
│       └── Implementaciones de servicios
│
├── storage
│   └── Integración con almacenamiento externo
│
└── common
    └── Componentes compartidos y excepciones
```

## Funcionalidades contempladas

- Administración de productos.
- Administración de categorías.
- Asociación de productos con múltiples categorías.
- Gestión de imágenes y portadas.
- Almacenamiento de imágenes en Cloudflare R2.
- Administración de enlaces externos de comercialización.
- Gestión de plataformas externas.
- Autenticación de administradores.
- Gestión de configuraciones del sitio.
- Papelera de productos y eliminación lógica.

## Requisitos previos

Para ejecutar el proyecto en ambiente local se requiere:

- JDK 25.
- Maven.
- Docker Desktop.
- PostgreSQL mediante contenedor Docker.
- IDE compatible con Java, preferentemente IntelliJ IDEA.

## Configuración local

### Base de datos

El ambiente de desarrollo utiliza PostgreSQL mediante Docker.

Configuración actual:

| Propiedad     | Valor                    |
|---------------|--------------------------|
| Host          | `localhost`              |
| Puerto        | `5432`                   |
| Base de datos | `wildprint_db`           |
| Usuario       | `wildprint_user`         |
| Contraseña    | `wildprint_dev_password` |

La configuración de conexión se encuentra en:

`src/main/resources/application.yaml`

> Las credenciales indicadas son exclusivamente para desarrollo local. En ambientes productivos deben utilizarse variables de entorno y credenciales seguras.

### Migraciones

El proyecto utiliza Flyway para administrar las versiones del esquema de base de datos.

Ubicación:

`src/main/resources/db/migration/`

Las migraciones SQL se ejecutan automáticamente durante el inicio de la aplicación.

## Ejecución

Con Docker Desktop iniciado y PostgreSQL disponible, ejecutar desde la raíz del proyecto:

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

También es posible ejecutar la clase principal desde el IDE:

`WildPrintBackendApplication`

## API REST

Los servicios REST utilizan el prefijo:

`/api`

Rutas contempladas inicialmente:

| Recurso          | Ruta                                |
|------------------|-------------------------------------|
| Productos        | `/api/productos`                    |
| Categorías       | `/api/categorias`                   |
| Imágenes         | `/api/productos/{id}/imagenes`      |
| Enlaces de venta | `/api/productos/{id}/enlaces-venta` |
| Plataformas      | `/api/plataformas-venta`            |
| Autenticación    | `/api/autenticacion`                |
| Administradores  | `/api/administradores`              |
| Configuración    | `/api/configuracion`                |

La documentación OpenAPI estará disponible una vez habilitada la configuración correspondiente.

## Convenciones de desarrollo

- Código fuente y documentación técnica en español.
- Nombres de tablas y columnas en español, utilizando `snake_case`.
- Clases Java con nomenclatura `PascalCase`.
- Variables y métodos con nomenclatura `camelCase`.
- Identificadores UUID.
- Documentación JavaDoc en clases e interfaces.
- Persistencia administrada mediante Spring Data JPA.
- Control de cambios de base de datos mediante Flyway.
- Separación de responsabilidades entre controladores, servicios y repositorios.

## Información del desarrollador

**Ricardo Cruz**  
R'lyeh Software Solutions  
Proyecto: Wild Print

## Licencia

© Ricardo Cruz - Wild Print.

Proyecto de propiedad de Wild Print. Los términos definitivos de distribución y utilización se establecerán mediante la licencia correspondiente.

---

*Wild Print Backend — Versión inicial 0.0.1-SNAPSHOT.*