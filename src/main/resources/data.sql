INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(1, 'マイケル', true, '1993-05-01', 'micheal@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(2, 'ジョン', true, '1995-07-01', 'john@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(3, 'ジョニー', true, '1998-07-15', 'jonny@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(4, 'メイシー', false, '1999-07-01', 'macy@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(5, 'ケイト', false, '2001-07-01', 'kate@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(6, 'キム', false, '2004-07-01', 'kim@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(1, 1, 4, 0);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(2, 1, 5, 0);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(3, 1, 6, 0);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(4, 2, 4, 0);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(5, 2, 5, 0);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(6, 2, 6, 0);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(7, 3, 4, 0);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(8, 3, 5, 0);

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(9, 3, 6, 3);