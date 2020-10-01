INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)
VALUES

(1, '田島幸雄', true, '1993-07-01', 'otoko1@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL'),
(2, '井上陽介', true, '1995-07-01', 'otoko2@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL'),
(3, '飯田健', true, '1997-07-01', 'otoko3@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL'),
(4, '前田遼平', true, '1999-07-01', 'otoko4@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL'),

(5, '石田幸恵', false, '1993-07-01', 'onna1@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL'),
(6, '羽賀光', false, '1995-07-01', 'onna2@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL'),
(7, '飯島芽衣', false, '1997-07-01', 'onna3@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL'),
(8, '福島咲', false, '1999-07-01', 'onna4@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES

(1, 1, 5, 3),
(2, 1, 6, 0),
(3, 1, 7, 0),
(4, 1, 8, 0),
(5, 2, 5, 0),
(6, 2, 6, 0),
(7, 2, 7, 0),
(8, 2, 8, 0),
(9, 3, 5, 0),
(10, 3, 6, 0),
(11, 3, 7, 0),
(12, 3, 8, 0),
(13, 4, 5, 0),
(14, 4, 6, 0),
(15, 4, 7, 0),
(16, 4, 8, 0);

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES

(1, 1, 1, 'はじめまして！趣味は何ですか？'),
(1, 5, 2, 'こちらこそ！サッカーです。'),
(1, 1, 3, 'スポーティーですね！私もサッカーです。'),
(1, 5, 4, '一緒じゃないですか！？');