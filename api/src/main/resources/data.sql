INSERT
INTO usuario (codigo, email, senha, perfil)
VALUES (1, 'caneschi.danilo@gmail.com', '$2a$10$NvYbhVEx1hfnOC/Tnd.uXO0usXHMaxwSbrfTuiwvMA.4300EVInVW', 'ROLE_ADMIN') ON DUPLICATE KEY UPDATE
codigo=1;