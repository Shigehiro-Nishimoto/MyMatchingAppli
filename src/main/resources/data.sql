INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(1, 'Hikaru', true, '1999-05-01', 'hikaru@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(2, 'Yuka', false, '1997-07-01', 'yuka@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(3, 'Kensuke', true, '1997-07-015', 'kensuke@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(4, 'Yumie', false, '1997-07-01', 'yumie@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(1, 1, 2, 0);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(2, 1, 4, 1);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(3, 3, 2, 2);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(4, 3, 4, 3);

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(4, 3, 1, 'はじめまして。');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(4, 4, 2, 'こちらこそ。趣味はなんですか？');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(4, 3, 3, 'テニスです。');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(4, 4, 4, '一緒です（笑）');