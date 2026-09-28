-- CATEGORIAS
INSERT INTO tb_category (name) VALUES ('Chá Verde');
INSERT INTO tb_category (name) VALUES ('Chá Preto');
INSERT INTO tb_category (name) VALUES ('Chá Branco');
INSERT INTO tb_category (name) VALUES ('Chá Vermelho');
INSERT INTO tb_category (name) VALUES ('Infusão');

-- PRODUTOS
INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Chá Verde Jasmin', 'Chá verde rico em antioxidantes.', 124.50, 'https://exemplo.com/cha-verde.jpg', 1);
INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Chá Preto Grey', 'Chá preto clássico e saboroso.', 64.90, 'https://exemplo.com/cha-preto.jpg', 2);
INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Chá Branco Fresh', 'Chá branco suave e delicado.', 110.90, 'https://exemplo.com/cha-branco.jpg', 3);
INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Chá Vermelho Cream', 'Chá vermelho rico em vitaminas.', 74.10, 'https://exemplo.com/cha-vermelho.jpg', 4);
INSERT INTO tb_product (name, description, price, image_url, category_id) VALUES ('Infusão Happy', 'Infusão saborosa e vitamínica.', 54.60, 'https://exemplo.com/infusao.jpg', 5);

-- USUÁRIOS
INSERT INTO tb_user (name, email, password) VALUES ('Usuário 1', 'user1@email.com', '$2a$10$jIbTwbctd2Z5p4RGJylAd.5xmXS6bvHA.d.tCkII0sPX3ytEBB67W');
INSERT INTO tb_user (name, email, password) VALUES ('Usuário 2', 'user2@email.com', '$2a$10$LrP003UihfWhQodkDKcmxerhXAqZ0GTRYL5rBBOx7qWBXw7oc.joC');

-- ENDEREÇOS
INSERT INTO tb_address (street, number, complement, neighborhood, city, state, zip_code, user_id) VALUES ('Rua das Flores', '123', 'Apto 10', 'Centro','Pato Branco', 'PR', '85501-000', 1);
INSERT INTO tb_address (street, number, complement, neighborhood, city, state, zip_code, user_id) VALUES ('Rua Paraná', '500', NULL, 'Centro','Pato Branco', 'PR', '85501-100', 2);

-- PEDIDOS
-- Usuário 1:
INSERT INTO tb_order (date, user_id, address_street, address_number, address_complement, address_neighborhood, address_city, address_state, address_zip_code) VALUES (CURRENT_TIMESTAMP, 1, 'Rua das Flores', '123', 'Apto 10','Centro', 'Pato Branco', 'PR', '85501-000');
INSERT INTO tb_order (date, user_id, address_street, address_number, address_complement, address_neighborhood, address_city, address_state, address_zip_code) VALUES (CURRENT_TIMESTAMP, 1, 'Rua das Flores', '123', 'Apto 10','Centro', 'Pato Branco', 'PR', '85501-000');

-- Usuário 2:
INSERT INTO tb_order (date, user_id, address_street, address_number, address_complement, address_neighborhood, address_city, address_state, address_zip_code) VALUES (CURRENT_TIMESTAMP, 2, 'Rua Paraná', '500', NULL, 'Centro', 'Pato Branco', 'PR', '85501-100');

-- ITENS DOS PEDIDOS
-- Pedido 1:
INSERT INTO tb_order_item (order_id, product_id, quantity, price) VALUES (1, 1, 2, 124.50);
INSERT INTO tb_order_item (order_id, product_id, quantity, price) VALUES (1, 2, 1, 64.90);

-- Pedido 2:
INSERT INTO tb_order_item (order_id, product_id, quantity, price) VALUES (2, 3, 1, 110.90);

-- Pedido 3:
INSERT INTO tb_order_item (order_id, product_id, quantity, price) VALUES (3, 4, 2, 74.10);