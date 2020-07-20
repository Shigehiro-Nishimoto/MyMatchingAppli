INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(1, 'Hikaru', true, '1999-05-01', 'hikaru@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO members (id, name, sex, birthday, mailaddress, password, role)

VALUES(2, 'Yuka', false, '1997-07-01', 'yuka@yahoo.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL');

INSERT INTO matchings (matchingid, maleid, femaleid, state)
VALUES(1, 1, 2, 0);