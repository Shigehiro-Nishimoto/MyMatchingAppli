INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(1, '田中光', true, '1998-05-01', 'hikaru@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(2, '伊藤武', true, '2000-07-01', 'takeshi@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(3, '藤原健介', true, '1994-07-15', 'kensuke@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(4, '大島友美恵', false, '1990-07-01', 'yumie@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(5, '木島薫', false, '1989-07-01', 'kaoru@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');


INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(1, 1, 4, 0);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(2, 1, 5, 3);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(3, 2, 4, 1);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(4, 2, 5, 2);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(5, 3, 4, 2);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(6, 3, 5, 3);


INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(2, 1, 1, 'はじめまして。');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(2, 5, 2, 'こちらこそ。趣味はなんですか？');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(2, 1, 3, 'テニスです。');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(2, 5, 4, '一緒です（笑）');


INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(6, 3, 1, 'こんにちは。');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(6, 5, 2, '会いませんか？');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(6, 3, 3, 'いいですよ！');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(6, 5, 4, 'では新宿に７時で。');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(6, 3, 5, '了解です！');