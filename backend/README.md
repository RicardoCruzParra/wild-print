# Wild Print Backend

**Backend de la plataforma Wild Print**

Backend desarrollado para proporcionar servicios REST destinados a la administración del catálogo digital, productos, categorías, imágenes y enlaces externos de comercialización de Wild Print.

---

## Información del proyecto

| Propiedad | Valor |
|---|---|
| Proyecto | Wild Print Backend |
| Versión | 0.0.1-SNAPSHOT |
| Organización | R'lyeh Software Solutions |
| Desarrollador | Ricardo Cruz |
| Grupo Maven | `cl.wildprint` |
| Artefacto | `wild-print-backend` |
| Lenguaje | Java 25 |
| Estado | Desarrollo |

## Descripción

Wild Print es una plataforma orientada a la presentación y comercialización de modelos digitales y diseños 3D.

Este backend proporciona la infraestructura necesaria para administrar el catálogo de productos, organizar categorías, gestionar imágenes y mantener enlaces hacia plataformas externas de comercialización.

El proyecto utiliza una **arquitectura modular monolítica**, manteniendo separadas las responsabilidades de configuración, persistencia, lógica de negocio, exposición de servicios REST y almacenamiento de archivos.

La arquitectura contempla la posibilidad de incorporar nuevas funcionalidades sin introducir complejidad innecesaria ni depender de un proveedor específico de almacenamiento.

## Tecnologías

| Tecnología | Utilización |
|---|---|
| Java 25 | Lenguaje de programación |
| Spring Boot 4.1.2 | Framework principal |
| Spring Web MVC | Desarrollo de API REST |
| Spring Data JPA | Persistencia y acceso a datos |
| Spring Security | Seguridad y autenticación |
| Spring Session JDBC | Gestión de sesiones persistentes |
| Spring Security CSRF | Protección contra solicitudes CSRF |
| PostgreSQL | Base de datos relacional |
| Flyway | Control de versiones del esquema |
| Hibernate | ORM |
| Lombok | Reducción de código repetitivo |
| Maven | Gestión de dependencias y construcción |
| AWS SDK for Java 2.x (S3) | Comunicación con almacenamiento compatible con S3 |
| Cloudflare R2 | Almacenamiento externo de imágenes |
| Sistema de archivos local | Almacenamiento durante desarrollo |
| Springdoc OpenAPI | Documentación de API |

## Arquitectura

El proyecto utiliza una arquitectura modular monolítica. Los componentes se organizan según sus responsabilidades.

```text
cl.wildprint.wild_print_backend
│
├── common
│   └── Componentes compartidos y excepciones
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
└── storage
    ├── modelo
    │   └── ArchivoAlmacenado
    │
    ├── proveedor
    │   ├── CloudflareR2Almacenamiento
    │   └── LocalAlmacenamiento
    │
    ├── servicio
    │   └── AlmacenamientoServicio
    │
    └── validacion
        └── ValidadorArchivo
```

La estructura puede ampliarse a medida que se incorporen nuevas funcionalidades.

## Almacenamiento de archivos

El backend dispone de una abstracción de almacenamiento que permite trabajar con diferentes proveedores sin acoplar la lógica de negocio a una implementación específica.

### Arquitectura de almacenamiento

La interfaz `AlmacenamientoServicio` define las operaciones disponibles:

- `almacenar(MultipartFile archivo)`
- `eliminar(String clave)`

Las implementaciones actuales son:

| Implementación | Responsabilidad |
|---|---|
| `LocalAlmacenamiento` | Guarda y elimina archivos en el sistema de archivos local |
| `CloudflareR2Almacenamiento` | Guarda y elimina archivos en Cloudflare R2 |

Ambas implementaciones utilizan:

- `ArchivoAlmacenado`: modelo que representa la clave y URL del archivo.
- `ValidadorArchivo`: componente centralizado de validación.

### Selección del proveedor

El proveedor se selecciona mediante la propiedad `wild-print.almacenamiento`.

**Almacenamiento local:**

```yaml
wild-print:
  almacenamiento: local
  ruta-local: ${WILDPRINT_RUTA_LOCAL:./uploads}
```

Los archivos se almacenan en el directorio configurado. Por defecto, se utiliza `./uploads`.

Los archivos locales se exponen mediante la ruta HTTP:

```text
/archivos/**
```

**Cloudflare R2:**

```yaml
wild-print:
  almacenamiento: r2
  endpoint: ${CLOUDFLARE_R2_ENDPOINT:}
  bucket: ${CLOUDFLARE_R2_BUCKET:}
  access-key: ${CLOUDFLARE_R2_ACCESS_KEY:}
  secret-key: ${CLOUDFLARE_R2_SECRET_KEY:}
  url-publica: ${CLOUDFLARE_R2_PUBLIC_URL:}
```

La integración utiliza AWS SDK for Java 2.x mediante el protocolo S3 compatible con Cloudflare R2.

La configuración del cliente S3 se realiza mediante `AlmacenamientoConfiguracion`.

Las implementaciones se activan condicionalmente mediante `@ConditionalOnProperty`, evitando crear clientes o proveedores que no correspondan al ambiente seleccionado.

### Validaciones actuales

| Validación | Configuración |
|---|---|
| Tamaño máximo | 5 MB |
| JPEG | Permitido |
| PNG | Permitido |
| WebP | Permitido |
| Archivo vacío | Rechazado |
| Otros formatos | Rechazados |

La validación del contenido real de los archivos y sus firmas binarias queda contemplada como refuerzo de seguridad pendiente.

## Persistencia y base de datos

El proyecto utiliza PostgreSQL como motor de base de datos relacional.

La persistencia se administra mediante Spring Data JPA e Hibernate.

### Configuración Hibernate

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
    open-in-view: false
    show-sql: false
```

Se utiliza `ddl-auto: validate` para comprobar que el modelo de entidades sea compatible con el esquema existente, evitando que Hibernate modifique automáticamente la estructura de la base de datos.

### Migraciones

Flyway administra las versiones del esquema de base de datos.

Ubicación:

```text
src/main/resources/db/migration/
```

Las migraciones SQL se ejecutan durante el inicio de la aplicación.

La estructura de la base de datos se mantiene bajo control de versiones mediante scripts de migración.

## Seguridad y autenticación

La seguridad de la aplicación se implementa mediante Spring Security.

Actualmente contempla:

- Autenticación de administradores.
- Gestión de sesiones mediante Spring Session JDBC.
- Persistencia de sesiones en PostgreSQL.
- Protección CSRF mediante `CookieCsrfTokenRepository`.
- Configuración CORS para permitir la comunicación con el frontend durante desarrollo.
- Restricción de acceso a endpoints administrativos.
- Acceso público de lectura a determinados recursos del catálogo.
- Acceso público de lectura a imágenes locales mediante `GET /archivos/**`.

Las solicitudes administrativas requieren autenticación.

La política de sesiones utiliza `SessionCreationPolicy.IF_REQUIRED`.

### Configuración CORS

Durante el desarrollo se permite el origen:

```text
http://localhost:5173
```

La configuración permite el envío de credenciales para trabajar con sesiones entre frontend y backend.

La configuración de orígenes deberá ajustarse para los ambientes productivos.

## Requisitos previos

Para ejecutar el proyecto en ambiente local se requiere:

- JDK 25.
- Maven (o Maven Wrapper incluido en el proyecto).
- PostgreSQL.
- IDE compatible con Java, preferentemente IntelliJ IDEA.
- DBeaver u otra herramienta de administración de PostgreSQL (opcional).

Docker no es un requisito para el ambiente de desarrollo actual, ya que PostgreSQL se encuentra instalado y ejecutándose localmente.

## Configuración local

### Base de datos

Configuración actual del ambiente de desarrollo:

| Propiedad | Valor |
|---|---|
| Host | `localhost` |
| Puerto | `5432` |
| Base de datos | `wildprint_db` |
| Usuario | `wildprint_user` |
| Contraseña | `wildprint_dev_password` |

La configuración de conexión se encuentra en:

```text
src/main/resources/application.yaml
```

La contraseña de desarrollo puede configurarse mediante:

```text
WILDPRINT_DB_PASSWORD
```

Las credenciales indicadas son exclusivamente para desarrollo local. En ambientes productivos deben utilizarse variables de entorno y credenciales seguras.

### Almacenamiento local

Por defecto, los archivos se guardan en:

```text
./uploads
```

La ruta puede modificarse mediante:

```text
WILDPRINT_RUTA_LOCAL
```

El directorio se crea automáticamente cuando se almacena el primer archivo.

### Configuración Cloudflare R2

Para utilizar R2 se deben configurar las siguientes variables de entorno:

```text
CLOUDFLARE_R2_ENDPOINT
CLOUDFLARE_R2_BUCKET
CLOUDFLARE_R2_ACCESS_KEY
CLOUDFLARE_R2_SECRET_KEY
CLOUDFLARE_R2_PUBLIC_URL
```

La integración está implementada en el backend. Su utilización requiere disponer de una cuenta, bucket, credenciales y dominio público correctamente configurados.

## Ejecución

Desde la raíz del proyecto:

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

También es posible ejecutar la clase principal desde el IDE:

```text
WildPrintBackendApplication
```

Por defecto, Spring Boot inicia el servidor integrado en el puerto `8080`.

## Construcción y despliegue

El proyecto utiliza Maven para compilar, validar y empaquetar la aplicación.

### Compilación y validación

```bash
./mvnw clean verify
```

En Windows:

```bash
mvnw.cmd clean verify
```

### Generación del artefacto

```bash
./mvnw clean package
```

El archivo ejecutable se genera en:

```text
target/wild-print-backend-0.0.1-SNAPSHOT.jar
```

### Ejecución del artefacto

```bash
java -jar target/wild-print-backend-0.0.1-SNAPSHOT.jar
```

### Despliegue en un servidor

El despliegue puede realizarse en un servidor compatible con Java 25.

El procedimiento general contempla:

1. Preparar el servidor y disponer de un entorno Java compatible.
2. Configurar las variables de entorno necesarias.
3. Preparar una instancia de PostgreSQL accesible desde la aplicación.
4. Configurar las credenciales y parámetros de almacenamiento.
5. Generar el artefacto JAR mediante Maven.
6. Transferir el artefacto al servidor.
7. Iniciar la aplicación mediante `java -jar`.
8. Configurar el acceso HTTPS y el dominio del backend mediante la infraestructura correspondiente.

La plataforma de hosting, automatización CI/CD y estrategia de despliegue productivo se definirán en una etapa posterior.

No se encuentra establecido todavía un despliegue productivo.

## API REST

Los servicios REST utilizan el prefijo:

```text
/api
```

Recursos contemplados:

| Recurso | Ruta |
|---|---|
| Productos | `/api/productos` |
| Categorías | `/api/categorias` |
| Imágenes | `/api/productos/{id}/imagenes` |
| Enlaces de venta | `/api/productos/{id}/enlaces-venta` |
| Plataformas | `/api/plataformas-venta` |
| Autenticación | `/api/autenticacion` |
| Administradores | `/api/administradores` |
| Configuración | `/api/configuracion` |

Las rutas corresponden al alcance previsto de la API. Su disponibilidad dependerá de la implementación de cada recurso.

### Documentación OpenAPI

El proyecto contempla Springdoc OpenAPI para documentar los servicios REST.

Cuando la dependencia y configuración correspondiente estén habilitadas, la documentación podrá consultarse mediante:

```text
/swagger-ui/index.html
/v3/api-docs
```

## Funcionalidades del proyecto

### Alcance funcional contemplado

- Administración de productos.
- Administración de categorías.
- Asociación de productos con múltiples categorías.
- Gestión de imágenes y portadas.
- Almacenamiento local y Cloudflare R2.
- Administración de enlaces externos de comercialización.
- Gestión de plataformas externas.
- Autenticación de administradores.
- Gestión de configuraciones del sitio.
- Papelera de productos y eliminación lógica.

### Próximas etapas

- Completar los servicios de administración y catálogo.
- Completar la integración de imágenes con los productos.
- Incorporar las funcionalidades restantes de la API.
- Desarrollar el frontend con React y TypeScript.
- Integrar frontend y backend.
- Implementar pruebas funcionales y de integración.
- Configurar el despliegue productivo y automatización de entregas.

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
- Uso de interfaces para desacoplar implementaciones.
- Configuración mediante propiedades externas y variables de entorno cuando corresponda.

## Información del desarrollador

**Ricardo Cruz**  
R'lyeh Software Solutions  
Proyecto: Wild Print

## Licencia

© Ricardo Cruz - Wild Print.

Proyecto de propiedad de Wild Print. Los términos definitivos de distribución y utilización se establecerán mediante la licencia correspondiente.

---

*Wild Print Backend — Versión 0.0.1-SNAPSHOT.*