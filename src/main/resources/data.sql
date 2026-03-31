INSERT INTO carousel_slides (id, image, image_dark, type, title, subtitle, sort_order, status)
VALUES
    (1, 'https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&q=80', 'https://images.unsplash.com/photo-1512918760513-95f1fde64ccf?auto=format&fit=crop&q=80', 'hero', '在城市心脏，安放诗意', '繁华与宁静仅一步之遥，让您在都市节奏中拥有专属的静谧港湾', 1, TRUE),
    (2, 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?auto=format&fit=crop&q=80', 'https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?auto=format&fit=crop&q=80', 'hero', '生活美学的实践场', '精选的器物，考究的陈设，在日常中遇见不期而遇的惊喜与感动', 2, TRUE),
    (3, 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&q=80', 'https://images.unsplash.com/photo-1554995207-c18c203602cb?auto=format&fit=crop&q=80', 'hero', '连接人与城���的温度', '不仅是落脚点，更是探索城市、感受生活的贴心伙伴与灵感源泉', 3, TRUE),
    (4, 'https://images.unsplash.com/photo-1502005229762-cf1e25374e77?auto=format&fit=crop&q=80', 'https://images.unsplash.com/photo-1505693314120-0d443867891c?auto=format&fit=crop&q=80', 'hero', '重新定义都市栖居', '让每一次归家都充满仪式感，在快节奏中享受慢生活的真正惬意', 4, TRUE)
ON DUPLICATE KEY UPDATE
    image = VALUES(image),
    image_dark = VALUES(image_dark),
    type = VALUES(type),
    title = VALUES(title),
    subtitle = VALUES(subtitle),
    sort_order = VALUES(sort_order),
    status = VALUES(status);

