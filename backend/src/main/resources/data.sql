-- ddl-auto: update 시 중복 데이터 삽입 방지
INSERT INTO board_type (board_name)
SELECT 'free'
    WHERE NOT EXISTS (SELECT 1 FROM board_type WHERE board_name = 'free');

INSERT INTO board_type (board_name)
SELECT 'information'
    WHERE NOT EXISTS (SELECT 1 FROM board_type WHERE board_name = 'information');

-- category 조건부 삽입
INSERT INTO category (name, color, type, description)
SELECT '강아지', NULL, 'BOARD', '강아지 관련 게시글 카테고리'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = '강아지' AND type = 'BOARD');


INSERT INTO category (name, color, type, description)
SELECT '고양이', NULL, 'BOARD', '고양이 관련 게시글 카테고리'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = '고양이' AND type = 'BOARD');

INSERT INTO category (name, color, type, description)
SELECT '햄스터', NULL, 'BOARD', '햄스터 관련 게시글 카테고리'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = '햄스터' AND type = 'BOARD');

INSERT INTO category (name, color, type, description)
SELECT '물고기', NULL, 'BOARD', '물고기 관련 게시글 카테고리'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = '물고기' AND type = 'BOARD');

INSERT INTO category (name, color, type, description)
SELECT '뱀', NULL, 'BOARD', '뱀 관련 게시글 카테고리'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = '뱀' AND type = 'BOARD');

-- AI 유저 조건부 삽입
INSERT INTO `user` (email, password, nickname, user_type, enabled)
SELECT 'ai@chatgps.com', 'AI_PASSWORD_PLACEHOLDER', 'ChatGPS', 'AI', 1
    WHERE NOT EXISTS (SELECT 1 FROM `user` WHERE email = 'ai@chatgps.com');
