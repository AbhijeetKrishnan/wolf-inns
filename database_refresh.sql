DROP TRIGGER before_hotels_insert;
DROP TRIGGER before_hotels_update;
DROP TRIGGER before_service_staff_insert;
DROP TRIGGER before_service_staff_update;
DROP TRIGGER before_staff_update;
DROP TRIGGER before_occupied_presidential_suite_insert;
DROP TRIGGER before_occupied_presidential_suite_update;
DROP TRIGGER before_occupied_presidential_suite_delete;
DROP TRIGGER before_service_records_insert;
DROP TRIGGER before_service_records_update;
DROP TRIGGER before_stay_insert;
DROP TRIGGER before_stay_update;

delete from service_records;
delete from stays;
delete from billing_info;
delete from customers;
delete from occupied_presidential_suite;
delete from rooms;
delete from service_staff;
delete from hotels;
delete from staff;
delete from room_categories_services;
delete from services;
delete from payment_methods;
delete from room_categories;
delete from departments;
delete from job_titles;


drop table service_records;
drop table stays;
drop table billing_info;
drop table customers;
drop table occupied_presidential_suite;
drop table rooms;
drop table service_staff;
drop table hotels;
drop table staff;
drop table room_categories_services;
drop table services;
drop table payment_methods;
drop table room_categories;
drop table departments;
drop table job_titles;



CREATE TABLE job_titles (
  titleCode CHAR(4) NOT NULL,
  titleDesc VARCHAR(50),
  PRIMARY KEY (titleCode)
);

CREATE TABLE departments (
  deptCode CHAR(4) NOT NULL,
  deptDesc VARCHAR(50),
  PRIMARY KEY (deptCode)
);

CREATE TABLE room_categories (
  categoryCode CHAR(4) NOT NULL,
  categoryDesc VARCHAR(50),
  PRIMARY KEY (categoryCode)
);

CREATE TABLE payment_methods (
  payMethodCode CHAR(4) NOT NULL,
  payMethodDesc VARCHAR(50),
  PRIMARY KEY (payMethodCode)
);

CREATE TABLE services (
  serviceCode CHAR(4) NOT NULL,
  serviceDesc VARCHAR(50),
  charge DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (serviceCode)
);

CREATE TABLE room_categories_services (
  categoryCode CHAR(4) NOT NULL,
  serviceCode CHAR(4) NOT NULL,
  PRIMARY KEY (categoryCode, serviceCode),
  FOREIGN KEY (categoryCode) REFERENCES room_categories(categoryCode),
  FOREIGN KEY (serviceCode) REFERENCES services(serviceCode)
);

CREATE TABLE staff (
  staffId INT(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  titleCode CHAR(4) NOT NULL,
  deptCode CHAR(4) NOT NULL,
  address VARCHAR(75),
  city VARCHAR(50),
  state CHAR(2),
  phone VARCHAR(20) NOT NULL,
  DOB DATE,
  PRIMARY KEY (staffId),
  FOREIGN KEY (titleCode) REFERENCES job_titles(titleCode),
  FOREIGN KEY (deptCode) REFERENCES departments(deptCode)
);


CREATE TABLE hotels (
  hotelId INT(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  address VARCHAR(75) NOT NULL,
  city VARCHAR(50) NOT NULL,
  state CHAR(2) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  managerId INT(9) UNSIGNED NOT NULL UNIQUE,
  PRIMARY KEY (hotelId),
  FOREIGN KEY (managerId) REFERENCES staff(staffId)
);


CREATE TABLE service_staff (
  staffId INT(9) UNSIGNED NOT NULL,
  hotelId INT(9) UNSIGNED NOT NULL,  
  PRIMARY KEY (staffId, hotelId),
  FOREIGN KEY (staffId) REFERENCES staff(staffId),
  FOREIGN KEY (hotelId) REFERENCES hotels(hotelId)
);

CREATE TABLE rooms (
  hotelId INT(9) UNSIGNED NOT NULL,
  roomNumber VARCHAR(5) NOT NULL,
  maxAllowedOcc TINYINT NOT NULL,
  rate DECIMAL(7,2) NOT NULL,
  categoryCode CHAR(4) NOT NULL,
  available CHAR(1) NOT NULL,
  PRIMARY KEY (hotelId, roomNumber),
  FOREIGN KEY (hotelId) REFERENCES hotels(hotelId),
  FOREIGN KEY (categoryCode) REFERENCES room_categories(categoryCode)
);

CREATE TABLE occupied_presidential_suite (
  hotelId INT(9) UNSIGNED NOT NULL,
  roomNumber VARCHAR(5) NOT NULL,
  cateringStaffId INT(9) UNSIGNED NOT NULL UNIQUE,
  roomServiceStaffId INT(9) UNSIGNED NOT NULL UNIQUE,
  PRIMARY KEY (hotelId, roomNumber),
  FOREIGN KEY (hotelId, roomNumber) REFERENCES rooms(hotelId, roomNumber),
  FOREIGN KEY (cateringStaffId) REFERENCES staff(staffId),
  FOREIGN KEY (roomServiceStaffId) REFERENCES staff(staffId)
);

CREATE TABLE customers (
  customerId INT(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  DOB DATE,
  phone VARCHAR(20),
  email VARCHAR(75),
  PRIMARY KEY (customerId)
);


CREATE TABLE billing_info (
  billingId INT(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  responsiblePartySSN CHAR(9) NOT NULL,
  address VARCHAR(75),
  city VARCHAR(50),
  state CHAR(2),
  payMethodCode CHAR(4) NOT NULL,
  cardNumber VARCHAR(25),
  totalCharges DECIMAL(10,2),
  PRIMARY KEY (billingId),
  FOREIGN KEY (payMethodCode) REFERENCES payment_methods(payMethodCode)
);


CREATE TABLE stays (
  stayId INT(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  hotelId INT(9) UNSIGNED NOT NULL,
  roomNumber VARCHAR(5) NOT NULL,
  customerId INT(9) UNSIGNED NOT NULL,
  numOfGuests TINYINT NOT NULL,
  checkinDate DATE,
  checkinTime TIME,
  checkoutDate DATE,
  checkoutTime TIME,
  billingId INT(9) UNSIGNED NOT NULL UNIQUE,
  PRIMARY KEY (stayId),
  FOREIGN KEY (hotelId, roomNumber) REFERENCES rooms(hotelId, roomNumber),
  FOREIGN KEY (customerId) REFERENCES customers(customerId),
  FOREIGN KEY (billingId) REFERENCES billing_info(billingId)
);


CREATE TABLE service_records (
  stayId INT(9) UNSIGNED NOT NULL,
  serviceCode CHAR(4) NOT NULL,
  staffId INT(9) UNSIGNED NOT NULL,
  serviceDate DATE NOT NULL,
  serviceTime TIME NOT NULL,
  PRIMARY KEY (stayId, serviceDate, serviceTime),
  FOREIGN KEY (stayId) REFERENCES stays(stayId),
  FOREIGN KEY (serviceCode) REFERENCES services(serviceCode),
  FOREIGN KEY (staffId) REFERENCES service_staff(staffId)
);

ALTER TABLE staff AUTO_INCREMENT=100;
ALTER TABLE hotels AUTO_INCREMENT=1;
ALTER TABLE customers AUTO_INCREMENT=1001;
ALTER TABLE billing_info AUTO_INCREMENT=1;
ALTER TABLE stays AUTO_INCREMENT=1;

insert into job_titles values('CEO', 'Chief Executive Officer');
insert into job_titles values('MNGR', 'Manager');
insert into job_titles values('CATS', 'Catering Staff');
insert into job_titles values('RSST', 'Room Service Staff');
insert into job_titles values('FREP', 'Front desk representative');
insert into job_titles values('GYST', 'Gym staff');
insert into job_titles values('DCST', 'Dry cleaning staff');
insert into departments values('FRND', 'Front Office');
insert into departments values('MNGD', 'Managerial');
insert into departments values('EXED', 'Executive');
insert into departments values('SRVD', 'Service');
insert into room_categories values('DLXR', 'Deluxe Room');
insert into room_categories values('ECON', 'Economy Room');
insert into room_categories values('EXES', 'Executive Suite');
insert into room_categories values('PRES', 'Presidential Suite');
insert into payment_methods values('CCON', 'Credit Card');
insert into payment_methods values('CHEK', 'Check');
insert into payment_methods values('CASH', 'Cash');
insert into payment_methods values('CCWF', 'WolfInns Credit Card');
insert into services values('PHSV', 'Phone Service', '5');
insert into services values('DRCL', 'Dry Cleaning', '16');
insert into services values('GYMS', 'Gyms', '15');
insert into services values('RMSV', 'Room Service', '10');
insert into services values('SPRQ', 'Special Requests', '20');
insert into services values('CTSV', 'Catering Service', '10');
insert into services values('CKIN', 'Check-In', '0');
insert into services values('CKOT', 'Check-Out', '0');
insert into room_categories_services values('DLXR', 'PHSV');
insert into room_categories_services values('DLXR', 'DRCL');
insert into room_categories_services values('DLXR', 'GYMS');
insert into room_categories_services values('DLXR', 'RMSV');
insert into room_categories_services values('DLXR', 'SPRQ');
insert into room_categories_services values('DLXR', 'CTSV');
insert into room_categories_services values('DLXR', 'CKIN');
insert into room_categories_services values('DLXR', 'CKOT');
insert into room_categories_services values('PRES', 'PHSV');
insert into room_categories_services values('PRES', 'DRCL');
insert into room_categories_services values('PRES', 'GYMS');
insert into room_categories_services values('PRES', 'RMSV');
insert into room_categories_services values('PRES', 'SPRQ');
insert into room_categories_services values('PRES', 'CTSV');
insert into room_categories_services values('PRES', 'CKIN');
insert into room_categories_services values('PRES', 'CKOT');
insert into room_categories_services values('EXES', 'PHSV');
insert into room_categories_services values('EXES', 'DRCL');
insert into room_categories_services values('EXES', 'GYMS');
insert into room_categories_services values('EXES', 'RMSV');
insert into room_categories_services values('EXES', 'SPRQ');
insert into room_categories_services values('EXES', 'CTSV');
insert into room_categories_services values('EXES', 'CKIN');
insert into room_categories_services values('EXES', 'CKOT');
insert into room_categories_services values('ECON', 'PHSV');
insert into room_categories_services values('ECON', 'DRCL');
insert into room_categories_services values('ECON', 'GYMS');
insert into room_categories_services values('ECON', 'RMSV');
insert into room_categories_services values('ECON', 'SPRQ');
insert into room_categories_services values('ECON', 'CTSV');
insert into room_categories_services values('ECON', 'CKIN');
insert into staff (name, titleCode, deptCode, address, city, state, phone, DOB) values ('Mary','MNGR','MNGD','90 ABC St , Raleigh NC 27','Raleigh','NC','654','1978-01-01');
insert into staff (name, titleCode, deptCode, address, city, state, phone, DOB) values ('John','MNGR','MNGD','798 XYZ St , Rochester NY 54','Rochester','NY','564','1973-01-01');
insert into staff (name, titleCode, deptCode, address, city, state, phone, DOB) values ('Carol','MNGR','MNGD','351 MH St , Greensboro NC 27','Greensboro','NC','546','1973-01-01');
insert into staff (name, titleCode, deptCode, address, city, state, phone, DOB) values ('Emma','FREP','FRND','49 ABC St , Raleigh NC 27','Raleigh','NC','547','1963-01-01');
insert into staff (name, titleCode, deptCode, address, city, state, phone, DOB) values ('Ava','CATS','SRVD','425 RG St , Raleigh NC 27','Raleigh','NC','777','1963-01-01');
insert into staff (name, titleCode, deptCode, address, city, state, phone, DOB) values ('Peter','MNGR','MNGD','475 RG St , Raleigh NC 27','Raleigh','NC','724','1966-01-01');
insert into staff (name, titleCode, deptCode, address, city, state, phone, DOB) values ('Olivia','FREP','FRND','325 PD St , Raleigh NC 27','Raleigh','NC','799','1991-01-01');
insert into staff (name, titleCode, deptCode, address, city, state, phone, DOB) values ('Hector','DCST','SRVD','123 LS St , Raleigh 27','Raleigh','NC','123','1960-01-01');
insert into staff (name, titleCode, deptCode, address, city, state, phone, DOB) values ('Garcia','GYST','SRVD','456 MH St , Raleigh 27','Raleigh','NC','234','1965-01-01');
insert into staff (name, titleCode, deptCode, address, city, state, phone, DOB) values ('Molina','RSST','SRVD','789 KA St , Raleigh 27','Raleigh','NC','345','1970-01-01');
insert into staff (name, titleCode, deptCode, address, city, state, phone, DOB) values ('Sundar','FREP','FRND','910 PB St , Raleigh 27','Raleigh','NC','456','1975-01-01');
insert into hotels (name, address, city, state, phone, managerId) values ('Hotel A','21 ABC St , Raleigh NC 27','Raleigh','NC','919','100');
insert into hotels (name, address, city, state, phone, managerId) values ('Hotel B','25 XYZ St , Rochester NY 54','Rochester','NY','718','101');
insert into hotels (name, address, city, state, phone, managerId) values ('Hotel C','29 PQR St , Greensboro NC 27','Greensboro','NC','984','102');
insert into hotels (name, address, city, state, phone, managerId) values ('Hotel D','28 GHW St , Raleigh NC 32','Raleigh','NC','920','105');
insert into service_staff values('103','1');
insert into service_staff values('104','1');
insert into service_staff values('106','4');
insert into service_staff values('107','1');
insert into service_staff values('108','1');
insert into service_staff values('109','2');
insert into service_staff values('110','3');
insert into rooms values('1','1','1','100','ECON','Y');
insert into rooms values('1','2','2','200','DLXR','Y');
insert into rooms values('2','3','1','100','ECON','Y');
insert into rooms values('3','2','3','1000','EXES','N');
insert into rooms values('4','1','4','5000','PRES','Y');
insert into rooms values('1','5','2','200','DLXR','Y');
insert into customers (name, DOB, phone, email) values('David','1980-05-13','123','david@gmail.com');
insert into customers (name, DOB, phone, email) values('Sarah','1987-03-12','456','sarah@gmail.com');
insert into customers (name, DOB, phone, email) values('Joseph','1971-09-23','789','joseph@gmail.com');
insert into customers (name, DOB, phone, email) values('Lucy','1993-12-25','213','lucy@gmail.com');
insert into billing_info (responsiblePartySSN, address, city, state, payMethodCode, cardNumber, totalCharges) values('593-9846','980 TRT St , Raleigh NC','Raleigh','NC','CCON','1052','431');
insert into billing_info (responsiblePartySSN, address, city, state, payMethodCode, cardNumber, totalCharges) values('777-8352','7720 MHT St , Greensboro NC','Greensboro','NC','CCWF','3020','774.25');
insert into billing_info (responsiblePartySSN, address, city, state, payMethodCode, cardNumber, totalCharges) values('858-9430','231 DRY St , Rochester NY 78','Rochester','NY','CCON','2497','510');
insert into billing_info (responsiblePartySSN, address, city, state, payMethodCode, cardNumber, totalCharges) values('440-9328','24 BST Dr , Dallas TX 14','Dallas','TX','CASH',null,'3005');
insert into stays (hotelId, roomNumber, customerId, numOfGuests, checkinDate, checkinTime, checkoutDate, checkoutTime, billingId) values('1','1','1001','1','2017-05-10','15:17:00','2017-05-13','10:22:00','1');
insert into stays (hotelId, roomNumber, customerId, numOfGuests, checkinDate, checkinTime, checkoutDate, checkoutTime, billingId) values('1','2','1002','2','2017-05-10','16:11:00','2017-05-13','09:27:00','2');
insert into stays (hotelId, roomNumber, customerId, numOfGuests, checkinDate, checkinTime, checkoutDate, checkoutTime, billingId) values('2','3','1003','1','2016-05-10','15:45:00','2016-05-14','11:10:00','3');
insert into stays (hotelId, roomNumber, customerId, numOfGuests, checkinDate, checkinTime, checkoutDate, checkoutTime, billingId) values('3','2','1004','2','2018-05-10','14:30:00','2018-05-12','10:00:00','4');
insert into service_records values('1','DRCL','107','2017-05-12','01:01:00');
insert into service_records values('1','GYMS','108','2017-05-12','01:02:00');
insert into service_records values('2','GYMS','108','2017-05-12','01:03:00');
insert into service_records values('3','RMSV','109','2016-05-13','01:04:00');
insert into service_records values('4','PHSV','110','2018-05-11','01:05:00');

CREATE TRIGGER before_hotels_insert
BEFORE INSERT ON hotels
FOR EACH ROW BEGIN
  SET @title = (SELECT titleCode FROM staff WHERE staffId = NEW.managerId);
  IF @title <> "MNGR" THEN
    SET @error = (SELECT * FROM not_manager);
  END IF;
END;


CREATE TRIGGER before_hotels_update
BEFORE UPDATE ON hotels
FOR EACH ROW BEGIN
  SET @title = (SELECT titleCode FROM staff WHERE staffId = NEW.managerId);
  IF @title <> "MNGR" THEN
    SET @error = (SELECT * FROM not_manager);
  END IF;
END;

CREATE TRIGGER before_service_staff_insert
BEFORE INSERT ON service_staff
FOR EACH ROW BEGIN
  SET @title = (SELECT titleCode FROM staff WHERE staffId = NEW.staffId);
  IF @title = "MNGR" OR @title = "CEO" THEN
    SET @error = (SELECT * FROM not_service_staff);
  END IF;
END;


CREATE TRIGGER before_service_staff_update
BEFORE UPDATE ON service_staff
FOR EACH ROW BEGIN
  IF NEW.staffId <> OLD.staffId THEN
    SET @error = (SELECT * FROM cannot_update_primary_key);
  END IF;

  SET @dedicated = (SELECT COUNT(*) FROM occupied_presidential_suite WHERE cateringStaffId=NEW.staffId OR roomServiceStaffId=NEW.staffId);
  IF @dedicated > 0 THEN
        SET @error = (SELECT * FROM service_staff_is_dedicated);
  END IF;
END;

CREATE TRIGGER before_staff_update
BEFORE UPDATE ON staff
FOR EACH ROW BEGIN  
  IF NEW.titleCode = "MNGR" OR NEW.titleCode = "CEO" THEN
    SET @service_staff = (SELECT COUNT(*) FROM service_staff WHERE staffId=NEW.staffId);
    IF @service_staff > 0 THEN
          SET @error = (SELECT * FROM assigned_service_staff);
    END IF;
  END IF;

  SET @dedicated = (SELECT COUNT(*) FROM occupied_presidential_suite WHERE cateringStaffId=NEW.staffId OR roomServiceStaffId=NEW.staffId);
  IF @dedicated > 0 THEN
    IF NEW.titleCode <> OLD.titleCode THEN
        SET @error = (SELECT * FROM service_staff_is_dedicated);
    END IF;
  END IF;

  IF OLD.titleCode = "MNGR" AND NEW.titleCode <> "MNGR" THEN
      SET @manager = (SELECT COUNT(*) FROM hotels WHERE managerId=NEW.staffId);
      IF @manager > 0 THEN
        SET @error = (SELECT * FROM manager_assigned_to_hotel);
      END IF;
  END IF;
END;


CREATE TRIGGER before_occupied_presidential_suite_insert
BEFORE INSERT ON occupied_presidential_suite
FOR EACH ROW BEGIN

  SET @room_category = (SELECT categoryCode FROM rooms WHERE hotelId = NEW.hotelId AND roomNumber = NEW.roomNumber);
  IF @room_category <> "PRES" THEN
    SET @error = (SELECT * FROM not_presidential_suite);
  END IF;

  SELECT hotelId INTO @hotelId FROM service_staff where staffId = NEW.cateringStaffId;
  IF @hotelId <> NEW.hotelId THEN
    SET @error = (SELECT * FROM catering_staff_not_assigned_to_hotel);
  END IF;

  SELECT hotelId INTO @hotelId FROM service_staff where staffId = NEW.roomServiceStaffId;
  IF @hotelId <> NEW.hotelId THEN
    SET @error = (SELECT * FROM room_service_staff_not_assigned_to_hotel);
  END IF;

  SET @activeStay = (SELECT COUNT(*) FROM stays WHERE hotelId = NEW.hotelId AND roomNumber = NEW.roomNumber AND checkoutDate IS NULL);
  IF @activeStay <> 1 THEN
    SET @error = (SELECT * FROM stay_not_active);
  END IF;

  SET @title = (SELECT titleCode FROM staff WHERE staffId = NEW.cateringStaffId);
  IF @title = "CATS" THEN
    SET @error = (SELECT * FROM not_catering_staff);
  END IF;

  SET @title = (SELECT titleCode FROM staff WHERE staffId = NEW.roomServiceStaffId);
  IF @title = "RSST" THEN
    SET @error = (SELECT * FROM not_room_service_staff);
  END IF;
END;

CREATE TRIGGER before_occupied_presidential_suite_update
BEFORE UPDATE ON occupied_presidential_suite
FOR EACH ROW BEGIN

  SET @room_category = (SELECT categoryCode FROM rooms WHERE hotelId = NEW.hotelId AND roomNumber = NEW.roomNumber);
  IF @room_category <> "PRES" THEN
    SET @error = (SELECT * FROM not_presidential_suite);
  END IF;

  SET @activeStay = (SELECT COUNT(*) FROM stays WHERE hotelId = NEW.hotelId AND roomNumber = NEW.roomNumber AND checkoutDate IS NULL);
  IF @activeStay <> 1 THEN
    SET @error = (SELECT * FROM stay_not_active);
  END IF;

  SET @title = (SELECT titleCode FROM staff WHERE staffId = NEW.cateringStaffId);
  IF @title = "CATS" THEN
    SET @error = (SELECT * FROM not_catering_staff);
  END IF;

  SET @title = (SELECT titleCode FROM staff WHERE staffId = NEW.roomServiceStaffId);
  IF @title = "RSST" THEN
    SET @error = (SELECT * FROM not_room_service_staff);
  END IF;
END;


CREATE TRIGGER before_occupied_presidential_suite_delete
BEFORE DELETE ON occupied_presidential_suite
FOR EACH ROW BEGIN
  SET @activeStays = (SELECT COUNT(*) FROM stays WHERE hotelId = OLD.hotelId AND roomNumber = OLD.roomNumber AND checkoutDate IS NULL);
  IF @activeStays <> 0 THEN
    SET @error = (SELECT * FROM stay_still_active);
  END IF;
END;


CREATE TRIGGER before_stay_insert
BEFORE INSERT ON stays
FOR EACH ROW BEGIN
  SET @maxOccupancy = (SELECT maxAllowedOcc FROM rooms WHERE hotelId = NEW.hotelId AND roomNumber = NEW.roomNumber);
  IF NEW.numOfGuests < 0 OR NEW.numOfGuests > @maxOccupancy  THEN
    SET @error = (SELECT * FROM bad_num_of_guests);
  END IF;

  SET @room_occupied = (SELECT COUNT(*) FROM stays WHERE hotelId = 1 AND roomNumber = 101 AND (checkoutDate IS NULL OR checkoutDate = NEW.checkinDate));
  IF @room_occupied = 1 THEN
    SET @error = (SELECT * FROM room_is_occupied);
  END IF;
END;


CREATE TRIGGER before_stay_update
BEFORE UPDATE ON stays
FOR EACH ROW BEGIN
  SET @maxOccupancy = (SELECT maxAllowedOcc FROM rooms WHERE hotelId = NEW.hotelId AND roomNumber = NEW.roomNumber);
  IF NEW.numOfGuests < 0 OR NEW.numOfGuests > @maxOccupancy  THEN
    SET @error = (SELECT * FROM bad_num_of_guests);
  END IF;

  SET @room_occupied = (SELECT COUNT(*) FROM stays WHERE hotelId = 1 AND roomNumber = 101 AND (checkoutDate IS NULL OR checkoutDate = NEW.checkinDate));
  IF @room_occupied = 1 THEN
    SET @error = (SELECT * FROM room_is_occupied);
  END IF;
END;



DELIMITER //
CREATE TRIGGER before_service_records_insert
BEFORE INSERT ON service_records
FOR EACH ROW BEGIN

  SELECT hotelId, roomNumber INTO @stayHotelId, @stayRoomNumber FROM stays WHERE stayId = NEW.stayId;

  SET @title = (SELECT titleCode FROM staff WHERE staffId = NEW.staffId);
  IF @title = "CATS" THEN
    SET @assignedToPresidentialSuite = (SELECT COUNT(*) FROM occupied_presidential_suite WHERE cateringStaffId = NEW.staffId);
    IF @assignedToPresidentialSuite = 1 THEN
      SELECT hotelId, roomNumber INTO @hotelId, @roomNumber FROM occupied_presidential_suite WHERE cateringStaffId = NEW.staffId;      
      IF @hotelId <> @stayHotelId OR @roomNumber <> @stayRoomNumber THEN
        SET @error = (SELECT * FROM catering_staff_assigned_to_another_suite);
      END IF;
    END IF;
  END IF;
  IF @title = "RSST" THEN
    SET @assignedToPresidentialSuite = (SELECT COUNT(*) FROM occupied_presidential_suite WHERE roomServiceStaffId = NEW.staffId);
    IF @assignedToPresidentialSuite = 1 THEN
      SELECT hotelId, roomNumber INTO @hotelId, @roomNumber FROM occupied_presidential_suite WHERE roomServiceStaffId = NEW.staffId;
      IF @hotelId <> @stayHotelId OR @roomNumber <> @stayRoomNumber THEN
        SET @error = (SELECT * FROM room_service_staff_assigned_to_another_suite);
      END IF;
    END IF; 
  END IF;

  SELECT hotelId INTO @hotelId FROM service_staff where staffId = NEW.staffId;
  IF @hotelId <> @stayHotelId THEN
    SET @error = (SELECT * FROM staff_not_assigned_to_hotel);
  END IF;


  SET @activeStay = (SELECT COUNT(*) FROM stays WHERE stayId = NEW.stayId AND checkoutDate IS NULL);
  IF @activeStay < 1 THEN
    SET @error = (SELECT * FROM stay_not_active);
  END IF;

END; //
DELIMITER ;



DELIMITER //
CREATE TRIGGER before_service_records_update
BEFORE UPDATE ON service_records
FOR EACH ROW BEGIN

  SELECT hotelId, roomNumber INTO @stayHotelId, @stayRoomNumber FROM stays WHERE stayId = NEW.stayId;

  SET @title = (SELECT titleCode FROM staff WHERE staffId = NEW.staffId);
  IF @title = "CATS" THEN
    SET @assignedToPresidentialSuite = (SELECT COUNT(*) FROM occupied_presidential_suite WHERE cateringStaffId = NEW.staffId);
    IF @assignedToPresidentialSuite = 1 THEN
      SELECT hotelId, roomNumber INTO @hotelId, @roomNumber FROM occupied_presidential_suite WHERE cateringStaffId = NEW.staffId;      
      IF @hotelId <> @stayHotelId OR @roomNumber <> @stayRoomNumber THEN
        SET @error = (SELECT * FROM catering_staff_assigned_to_another_suite);
      END IF;
    END IF;
  END IF;
  IF @title = "RSST" THEN
    SET @assignedToPresidentialSuite = (SELECT COUNT(*) FROM occupied_presidential_suite WHERE roomServiceStaffId = NEW.staffId);
    IF @assignedToPresidentialSuite = 1 THEN
      SELECT hotelId, roomNumber INTO @hotelId, @roomNumber FROM occupied_presidential_suite WHERE roomServiceStaffId = NEW.staffId;
      IF @hotelId <> @stayHotelId OR @roomNumber <> @stayRoomNumber THEN
        SET @error = (SELECT * FROM room_service_staff_assigned_to_another_suite);
      END IF;
    END IF; 
  END IF;

  SELECT hotelId INTO @hotelId FROM service_staff where staffId = NEW.staffId;
  IF @hotelId <> @stayHotelId THEN
    SET @error = (SELECT * FROM staff_not_assigned_to_hotel);
  END IF;


  SET @activeStay = (SELECT COUNT(*) FROM stays WHERE stayId = NEW.stayId AND checkoutDate IS NULL);
  IF @activeStay < 1 THEN
    SET @error = (SELECT * FROM stay_not_active);
  END IF;

END; //
DELIMITER ;














