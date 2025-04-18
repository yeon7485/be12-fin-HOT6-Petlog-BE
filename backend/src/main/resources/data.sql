-- 게시판 종류 초기값 등록

-- ALTER TABLE board_type ADD CONSTRAINT uq_board_name UNIQUE (board_name); -- 최초 1회 실행 후 이 라인 주석처리!!

INSERT IGNORE INTO board_type (board_name, created_at, updated_at)
VALUES
    ('free', '2025-04-14', '2025-04-14'),
    ('information', '2025-04-14', '2025-04-14');