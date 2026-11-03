
-- =====================================================
-- WILD PRINT
-- V1: Esquema inicial
-- PostgreSQL
-- =====================================================


-- 1. PRODUCTOS
CREATE TABLE productos (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                           nombre VARCHAR(150) NOT NULL,
                           slug VARCHAR(180) NOT NULL UNIQUE,

                           descripcion_corta VARCHAR(300),
                           descripcion TEXT,

                           estado VARCHAR(20) NOT NULL DEFAULT 'BORRADOR',
                           destacado BOOLEAN NOT NULL DEFAULT FALSE,

                           fecha_creacion TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                           fecha_actualizacion TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                           fecha_eliminacion TIMESTAMPTZ,

                           CONSTRAINT chk_productos_estado
                               CHECK (estado IN ('BORRADOR', 'PUBLICADO', 'OCULTO'))
);

CREATE INDEX idx_productos_estado
    ON productos (estado);

CREATE INDEX idx_productos_fecha_eliminacion
    ON productos (fecha_eliminacion);


-- 2. CATEGORÍAS
CREATE TABLE categorias (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                            nombre VARCHAR(100) NOT NULL UNIQUE,
                            slug VARCHAR(120) NOT NULL UNIQUE,
                            descripcion VARCHAR(300),

                            fecha_creacion TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                            fecha_actualizacion TIMESTAMPTZ NOT NULL DEFAULT NOW()
);


-- 3. RELACIÓN PRODUCTOS - CATEGORÍAS
CREATE TABLE productos_categorias (
                                      producto_id UUID NOT NULL,
                                      categoria_id UUID NOT NULL,

                                      PRIMARY KEY (producto_id, categoria_id),

                                      CONSTRAINT fk_productos_categorias_producto
                                          FOREIGN KEY (producto_id)
                                              REFERENCES productos (id)
                                              ON DELETE CASCADE,

                                      CONSTRAINT fk_productos_categorias_categoria
                                          FOREIGN KEY (categoria_id)
                                              REFERENCES categorias (id)
                                              ON DELETE RESTRICT
);

CREATE INDEX idx_productos_categorias_categoria
    ON productos_categorias (categoria_id);


-- 4. IMÁGENES DE PRODUCTOS
CREATE TABLE imagenes_producto (
                                   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                   producto_id UUID NOT NULL,

                                   clave_archivo VARCHAR(500) NOT NULL,
                                   nombre_original VARCHAR(255) NOT NULL,
                                   tipo_contenido VARCHAR(100) NOT NULL,
                                   tamano_bytes BIGINT NOT NULL,

                                   texto_alternativo VARCHAR(250),

                                   orden INTEGER NOT NULL DEFAULT 0,
                                   es_portada BOOLEAN NOT NULL DEFAULT FALSE,

                                   fecha_creacion TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                                   CONSTRAINT fk_imagenes_producto
                                       FOREIGN KEY (producto_id)
                                           REFERENCES productos (id)
                                           ON DELETE CASCADE,

                                   CONSTRAINT chk_imagenes_tamano
                                       CHECK (tamano_bytes > 0),

                                   CONSTRAINT chk_imagenes_orden
                                       CHECK (orden >= 0)
);

CREATE INDEX idx_imagenes_producto_producto
    ON imagenes_producto (producto_id);

-- Máximo una portada por producto.
CREATE UNIQUE INDEX uk_imagenes_producto_portada
    ON imagenes_producto (producto_id)
    WHERE es_portada = TRUE;


-- 5. PLATAFORMAS DE VENTA
CREATE TABLE plataformas_venta (
                                   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                   codigo VARCHAR(50) NOT NULL UNIQUE,
                                   nombre VARCHAR(100) NOT NULL,

                                   activa BOOLEAN NOT NULL DEFAULT TRUE,

                                   fecha_creacion TIMESTAMPTZ NOT NULL DEFAULT NOW()
);


-- 6. ENLACES DE VENTA
CREATE TABLE enlaces_venta (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                               producto_id UUID NOT NULL,
                               plataforma_id UUID NOT NULL,

                               url_externa VARCHAR(1000) NOT NULL,
                               etiqueta VARCHAR(100),

                               activo BOOLEAN NOT NULL DEFAULT TRUE,

                               fecha_creacion TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                               fecha_actualizacion TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                               CONSTRAINT fk_enlaces_venta_producto
                                   FOREIGN KEY (producto_id)
                                       REFERENCES productos (id)
                                       ON DELETE CASCADE,

                               CONSTRAINT fk_enlaces_venta_plataforma
                                   FOREIGN KEY (plataforma_id)
                                       REFERENCES plataformas_venta (id)
                                       ON DELETE RESTRICT
);

CREATE INDEX idx_enlaces_venta_producto
    ON enlaces_venta (producto_id);

CREATE INDEX idx_enlaces_venta_plataforma
    ON enlaces_venta (plataforma_id);


-- 7. USUARIOS ADMINISTRADORES
CREATE TABLE usuarios_administradores (
                                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                          nombre_usuario VARCHAR(100) NOT NULL UNIQUE,
                                          correo VARCHAR(255) NOT NULL UNIQUE,
                                          clave_hash VARCHAR(255) NOT NULL,

                                          activo BOOLEAN NOT NULL DEFAULT TRUE,

                                          fecha_creacion TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                          fecha_actualizacion TIMESTAMPTZ NOT NULL DEFAULT NOW()
);


-- 8. CONFIGURACIÓN DEL SITIO
CREATE TABLE configuracion_sitio (
                                     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                     clave VARCHAR(100) NOT NULL UNIQUE,
                                     valor TEXT,

                                     fecha_actualizacion TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
