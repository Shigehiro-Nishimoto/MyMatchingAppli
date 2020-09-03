INSERT INTO matchingaite (matchingid)

VALUES(1);

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(1, 'マイケル', true, '1993-05-01', 'micheal@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(1, 1, 1, 'はじめまして！');

INSERT INTO message (matchingid, whospost, number, messagecontent)
VALUES(1, 4, 2, 'こちらこそ！');