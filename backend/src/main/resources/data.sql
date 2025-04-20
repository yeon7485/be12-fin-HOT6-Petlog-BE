-- 게시판 종류 초기값 등록



INSERT INTO board_type (board_name, created_at, updated_at)
VALUES
    ('free', '2025-04-14', '2025-04-14'),
    ('information', '2025-04-14', '2025-04-14');

INSERT INTO category (name, color, type, description)
VALUES
    ('강아지', NULL, 'BOARD', '강아지 관련 게시글 카테고리'),
    ('고양이', NULL, 'BOARD', '고양이 관련 게시글 카테고리'),
    ('햄스터', NULL, 'BOARD', '햄스터 관련 게시글 카테고리'),
    ('물고기', NULL, 'BOARD', '물고기 관련 게시글 카테고리'),
    ('뱀', NULL, 'BOARD', '뱀 관련 게시글 카테고리');

INSERT INTO `user` (email, password, nickname, user_type, enabled)
VALUES ('ai@chatgps.com', 'AI_PASSWORD_PLACEHOLDER', 'ChatGPS', 'AI', 1),
       ('admin@test.com', 'qwer1234', '관리자', 'ADMIN', 1);
