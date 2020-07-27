INSERT INTO reference(id, product_id, embed_id) VALUES (1, 'productTest', 1234);

INSERT INTO availability(id, reference_id, timestamp, available) VALUES
(1, 1, 1595693643, true),
(2, 1, 1595694843, true),
(3, 1, 1595696643, false),
(4, 1, 1595698443, false),
(5, 1, 1595700243, true);