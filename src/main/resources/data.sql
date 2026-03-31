INSERT INTO carousel_slides (id, image, image_dark, type, title, subtitle, sort_order, status)
VALUES
    (1, 'https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&q=80', 'https://images.unsplash.com/photo-1512918760513-95f1fde64ccf?auto=format&fit=crop&q=80', 'hero', '在城市心脏，安放诗意', '繁华与宁静仅一步之遥，让您在都市节奏中拥有专属的静谧港湾', 1, TRUE),
    (2, 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?auto=format&fit=crop&q=80', 'https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?auto=format&fit=crop&q=80', 'hero', '生活美学的实践场', '精选的器物，考究的陈设，在日常中遇见不期而遇的惊喜与感动', 2, TRUE),
    (4, 'https://images.unsplash.com/photo-1502005229762-cf1e25374e77?auto=format&fit=crop&q=80', 'https://images.unsplash.com/photo-1505693314120-0d443867891c?auto=format&fit=crop&q=80', 'hero', '重新定义都市栖居', '让每一次归家都充满仪式感，在快节奏中享受慢生活的真正惬意', 4, TRUE)
ON DUPLICATE KEY UPDATE
    image = VALUES(image),
    image_dark = VALUES(image_dark),
    type = VALUES(type),
    title = VALUES(title),
    subtitle = VALUES(subtitle),
    sort_order = VALUES(sort_order),
    status = VALUES(status);

INSERT INTO nav_categories (id, name) VALUES
(1, '平价公寓'),
(2, '精品民宿'),
(3, '海外房源'),
(4, '周边好物'),
(8, '青年驿站'),
(9, '商务出行')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO nav_items (category_id, title, description, link, image) VALUES
(1, '月租公寓', '灵活月付，轻松入住城市中心', '/apartments/monthly', 'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=800&q=80'),
(1, '短租公寓', '商旅出差，温馨舒适的临时港湾', '/apartments/short-term', 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&w=800&q=80'),
(1, '长租公寓', '稳定居所，享受品质生活服务', '/apartments/long-term', 'https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&w=800&q=80'),
(1, '整租公寓', '独享空间，私密自由的居住体验', '/apartments/entire', 'https://images.unsplash.com/photo-1484154218962-a1c002085d2f?auto=format&fit=crop&w=800&q=80'),
(1, '学生公寓', '毗邻高校，安全便捷的学习生活圈', '/apartments/student', 'https://images.unsplash.com/photo-1555854877-bab0e564b8d5?auto=format&fit=crop&w=800&q=80'),
(1, '白领公寓', '通勤便利，专为职场精英打造', '/apartments/professional', 'https://images.unsplash.com/photo-1497366216548-37526070297c?auto=format&fit=crop&w=800&q=80'),
(2, '特色民宿', '融入当地，体验独特的风土人情', '/homestays/featured', 'https://images.unsplash.com/photo-1587061949409-02df41d5e562?auto=format&fit=crop&w=800&q=80'),
(2, '热门民宿', '好评如潮，甄选高人气的优质房源', '/homestays/popular', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80'),
(2, '设计师民宿', '艺术空间，感受大师级的居住美学', '/homestays/designer', 'https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?auto=format&fit=crop&w=800&q=80'),
(2, '古村民宿', '归园田居，寻觅静谧的乡村时光', '/homestays/ancient-village', 'https://images.unsplash.com/photo-1599619351208-3e6c839d6828?auto=format&fit=crop&w=800&q=80'),
(2, '海景民宿', '面朝大海，享受惬意的海滨度假', '/homestays/seaview', 'https://images.unsplash.com/photo-1499793983690-e29da59ef1c2?auto=format&fit=crop&w=800&q=80'),
(2, '山居民宿', '天然氧吧，拥抱大自然的清新治愈', '/homestays/mountain', 'https://images.unsplash.com/photo-1449844908441-8829872d2607?auto=format&fit=crop&w=800&q=80'),
(2, '田园民宿', '采菊东篱，体验悠闲的农家生活', '/homestays/countryside', 'https://images.unsplash.com/photo-1500076656116-558758c991c1?auto=format&fit=crop&w=800&q=80'),
(3, '日本', '日式庭院，体验精致的和风雅宿', '/overseas/japan', 'https://images.unsplash.com/photo-1528360983277-13d901235fde?auto=format&fit=crop&w=800&q=80'),
(3, '韩国', '时尚潮流，感受首尔的都市魅力', '/overseas/korea', 'https://images.unsplash.com/photo-1517154421773-052f83c4217c?auto=format&fit=crop&w=800&q=80'),
(3, '泰国', '海岛风情，享受热带的慵懒时光', '/overseas/thailand', 'https://images.unsplash.com/photo-1528181304800-259b08848526?auto=format&fit=crop&w=800&q=80'),
(3, '新加坡', '花园城市，现代与自然的完美融合', '/overseas/singapore', 'https://images.unsplash.com/photo-1506318137071-a8bcbf90d704?auto=format&fit=crop&w=800&q=80'),
(3, '美国', '多元文化，探索自由国度的无限可能', '/overseas/usa', 'https://images.unsplash.com/photo-1512453979798-5ea904ac66de?auto=format&fit=crop&w=800&q=80'),
(3, '英国', '英伦风情，漫步泰晤士河畔的古典', '/overseas/uk', 'https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?auto=format&fit=crop&w=800&q=80'),
(3, '澳大利亚', '阳光沙滩，拥抱南半球的自然奇迹', '/overseas/australia', 'https://images.unsplash.com/photo-1523482580638-01c690b77091?auto=format&fit=crop&w=800&q=80'),
(3, '欧洲', '浪漫之旅，穿梭古老欧洲的艺术殿堂', '/overseas/europe', 'https://images.unsplash.com/photo-1471623432079-b009d30b6729?auto=format&fit=crop&w=800&q=80'),
(8, '背包客栈', '行者之家，为背包客提供温暖落脚点', '/hostels/backpacker', 'https://images.unsplash.com/photo-1555854877-bab0e564b8d5?auto=format&fit=crop&w=800&q=80'),
(8, '社交型青旅', '结伴同行，旅途中认识有趣的灵魂', '/hostels/social', 'https://images.unsplash.com/photo-1529156069898-49953e39b3ac?auto=format&fit=crop&w=800&q=80'),
(8, '胶囊旅馆', '太空舱体验，私密而经济的休憩空间', '/hostels/capsule', 'https://images.unsplash.com/photo-1596409849204-a14db25e3678?auto=format&fit=crop&w=800&q=80'),
(8, '露营基地', '星空露营，与大自然零距离接触', '/hostels/camping', 'https://images.unsplash.com/photo-1478131143081-80f7f84ca84d?auto=format&fit=crop&w=800&q=80'),
(8, '拼团出行', '组队探索，共享旅行乐趣与优惠', '/hostels/group', 'https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?auto=format&fit=crop&w=800&q=80'),
(9, 'CBD核心区', '位置优越，步行即达各大商务中心', '/business/cbd', 'https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?auto=format&fit=crop&w=800&q=80'),
(9, '会议设施', '设备齐全，满足各类商务会议需求', '/business/conference', 'https://images.unsplash.com/photo-1431540015161-0bf868a2d407?auto=format&fit=crop&w=800&q=80'),
(9, '打印复印', '便捷服务，随时解决办公文印烦恼', '/business/printing', 'https://images.unsplash.com/photo-1563200782-990e199f1c71?auto=format&fit=crop&w=800&q=80'),
(9, '商务中心', '专业配套，提供全方位的商务支持', '/business/center', 'https://images.unsplash.com/photo-1497215728101-856f4ea42174?auto=format&fit=crop&w=800&q=80'),
(9, '企业协议价', '专属优惠，助力企业降低差旅成本', '/business/corporate', 'https://images.unsplash.com/photo-1560179707-f14e90ef3623?auto=format&fit=crop&w=800&q=80'),
(4, '居家用品', '品质甄选，提升生活幸福感的小物', '/products/home', 'https://images.unsplash.com/photo-1484101403633-562f891dc89a?auto=format&fit=crop&w=800&q=80'),
(4, '厨房用具', '烹饪好帮手，让下厨成为一种享受', '/products/kitchen', 'https://images.unsplash.com/photo-1556910103-1c02745a30bf?auto=format&fit=crop&w=800&q=80'),
(4, '清洁用品', '高效洁净，轻松打造舒适居家环境', '/products/cleaning', 'https://images.unsplash.com/photo-1581578731117-926d8aa3702b?auto=format&fit=crop&w=800&q=80'),
(4, '装饰摆件', '艺术点缀，彰显主人独特的生活品味', '/products/decoration', 'https://images.unsplash.com/photo-1513519245088-0e12902e5a38?auto=format&fit=crop&w=800&q=80'),
(4, '床上用品', '亲肤好眠，每晚都能拥有的甜美梦境', '/products/bedding', 'https://images.unsplash.com/photo-1505693416388-b0346efee535?auto=format&fit=crop&w=800&q=80');
