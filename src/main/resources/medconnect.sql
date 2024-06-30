-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 30, 2024 at 11:55 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `medconnect`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE `appointments` (
  `appointment_id` int(11) NOT NULL,
  `appointment_patient_id` int(11) NOT NULL,
  `appointment_doctor_id` int(11) NOT NULL,
  `appointment_date` datetime NOT NULL,
  `appointment_reason` varchar(255) DEFAULT NULL,
  `appointment_status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`appointment_id`, `appointment_patient_id`, `appointment_doctor_id`, `appointment_date`, `appointment_reason`, `appointment_status`) VALUES
(1, 1, 7, '2023-06-15 09:00:00', 'General Checkup', 'RESCHEDULED'),
(2, 2, 7, '2023-05-11 10:00:00', 'Flu Symptoms', 'SCHEDULED'),
(4, 4, 5, '2023-05-13 14:00:00', 'Routine Blood Work', 'CANCELLED'),
(5, 5, 1, '2023-05-14 15:00:00', 'Allergy Testing', 'SCHEDULED'),
(6, 1, 1, '2024-06-19 09:00:00', 'General Checkup', 'COMPLETED'),
(7, 2, 2, '2024-06-19 10:00:00', 'Flu Symptoms', 'COMPLETED'),
(8, 3, 3, '2024-06-19 11:00:00', 'Back Pain', 'SCHEDULED'),
(9, 4, 4, '2024-06-20 09:00:00', 'Routine Blood Work', 'COMPLETED'),
(10, 5, 5, '2024-06-20 10:00:00', 'Allergy Testing', 'COMPLETED'),
(11, 1, 1, '2024-06-20 11:00:00', 'Follow-up Visit', 'SCHEDULED'),
(12, 2, 1, '2024-06-21 09:00:00', 'Diabetes Check', 'COMPLETED'),
(13, 3, 3, '2024-06-21 10:00:00', 'Heart Checkup', 'COMPLETED'),
(14, 4, 4, '2024-06-21 11:00:00', 'Consultation', 'SCHEDULED'),
(15, 5, 5, '2024-06-22 09:00:00', 'Routine Checkup', 'COMPLETED'),
(16, 1, 1, '2024-06-22 10:00:00', 'Blood Pressure Check', 'COMPLETED'),
(17, 2, 2, '2024-06-22 11:00:00', 'Vaccination', 'SCHEDULED'),
(21, 1, 8, '2024-06-29 09:00:00', 'Routine Checkup', 'SCHEDULED'),
(22, 2, 9, '2024-06-29 10:00:00', 'Follow-up Visit', 'SCHEDULED'),
(23, 3, 8, '2024-06-29 11:00:00', 'General Consultation', 'COMPLETED'),
(24, 4, 9, '2024-06-29 12:00:00', 'Routine Blood Work', 'COMPLETED'),
(25, 1, 8, '2024-06-30 09:00:00', 'Routine Checkup', 'SCHEDULED'),
(26, 2, 9, '2024-06-30 10:00:00', 'Follow-up Visit', 'SCHEDULED'),
(27, 3, 8, '2024-06-30 11:00:00', 'General Consultation', 'COMPLETED'),
(28, 4, 9, '2024-06-30 12:00:00', 'Routine Blood Work', 'COMPLETED'),
(29, 1, 8, '2024-07-01 09:00:00', 'Routine Checkup', 'SCHEDULED'),
(30, 2, 9, '2024-07-01 10:00:00', 'Follow-up Visit', 'SCHEDULED'),
(31, 3, 8, '2024-07-01 11:00:00', 'General Consultation', 'COMPLETED'),
(32, 4, 9, '2024-07-01 12:00:00', 'Routine Blood Work', 'COMPLETED'),
(33, 1, 8, '2024-07-02 09:00:00', 'Routine Checkup', 'SCHEDULED'),
(34, 2, 9, '2024-07-02 10:00:00', 'Follow-up Visit', 'SCHEDULED'),
(35, 3, 8, '2024-07-02 11:00:00', 'General Consultation', 'COMPLETED'),
(36, 4, 9, '2024-07-02 12:00:00', 'Routine Blood Work', 'COMPLETED'),
(37, 1, 8, '2024-07-03 09:00:00', 'Routine Checkup', 'SCHEDULED'),
(38, 2, 9, '2024-07-03 10:00:00', 'Follow-up Visit', 'SCHEDULED'),
(39, 3, 8, '2024-07-03 11:00:00', 'General Consultation', 'COMPLETED'),
(40, 4, 9, '2024-07-03 12:00:00', 'Routine Blood Work', 'COMPLETED');

-- --------------------------------------------------------

--
-- Table structure for table `billing`
--

CREATE TABLE `billing` (
  `bill_id` int(11) NOT NULL,
  `bill_appointment_id` int(11) NOT NULL,
  `bill_amount` decimal(10,2) NOT NULL,
  `bill_date` date NOT NULL,
  `bill_status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `billing`
--

INSERT INTO `billing` (`bill_id`, `bill_appointment_id`, `bill_amount`, `bill_date`, `bill_status`) VALUES
(1, 1, 100.00, '2023-05-10', 'PAID'),
(2, 2, 150.00, '2023-05-11', 'CANCELLED'),
(4, 4, 0.00, '2023-05-13', 'NA'),
(5, 5, 120.00, '2023-05-14', 'PENDING'),
(6, 23, 100.00, '2024-06-29', 'PAID'),
(7, 24, 150.00, '2024-06-29', 'PAID'),
(8, 27, 200.00, '2024-06-30', 'UNPAID'),
(9, 28, 250.00, '2024-06-30', 'PAID'),
(11, 32, 350.00, '2024-07-01', 'PAID'),
(12, 35, 400.00, '2024-07-02', 'UNPAID'),
(13, 36, 450.00, '2024-07-02', 'PAID'),
(14, 39, 500.00, '2024-07-03', 'PAID'),
(15, 40, 550.00, '2024-07-03', 'PAID');

-- --------------------------------------------------------

--
-- Table structure for table `medicalrecords`
--

CREATE TABLE `medicalrecords` (
  `record_id` int(11) NOT NULL,
  `record_appointment_id` int(11) NOT NULL,
  `record_diagnosis` text NOT NULL,
  `record_treatment` text NOT NULL,
  `record_prescription` text NOT NULL,
  `record_notes` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicalrecords`
--

INSERT INTO `medicalrecords` (`record_id`, `record_appointment_id`, `record_diagnosis`, `record_treatment`, `record_prescription`, `record_notes`) VALUES
(1, 1, 'Healthy', 'Regular exercise and balanced diet', 'None', 'Patient is in good health.'),
(4, 4, 'N/A', 'N/A', 'N/A', 'Appointment canceled by patient.'),
(6, 23, 'Back Pain', 'Physical Therapy', 'Painkillers', 'Needs regular exercises.'),
(7, 24, 'High Cholesterol', 'Diet and Exercise', 'Statins', 'Regular checkups required.'),
(8, 27, 'Migraine', 'Medication', 'Pain relievers', 'Needs follow-up.'),
(9, 28, 'Diabetes', 'Insulin Therapy', 'Insulin', 'Monitor blood sugar levels.'),
(11, 32, 'Hypertension', 'Medication', 'Antihypertensives', 'Regular BP checkups.'),
(12, 35, 'Gastritis', 'Diet', 'Antacids', 'Avoid spicy foods.'),
(13, 36, 'Arthritis', 'Pain Management', 'NSAIDs', 'Follow up in 3 months.'),
(14, 39, 'Eczema', 'Topical Steroids', 'Steroid Cream', 'Moisturize regularly.'),
(15, 40, 'Thyroid Disorder', 'Thyroxine', 'Thyroxine', 'Regular thyroid function tests.');

-- --------------------------------------------------------

--
-- Table structure for table `patients`
--

CREATE TABLE `patients` (
  `patient_id` int(11) NOT NULL,
  `patient_first_name` varchar(255) DEFAULT NULL,
  `patient_last_name` varchar(255) DEFAULT NULL,
  `patient_date_of_birth` date DEFAULT NULL,
  `patient_gender` varchar(255) DEFAULT NULL,
  `patient_phone_number` varchar(255) DEFAULT NULL,
  `patient_email` varchar(255) DEFAULT NULL,
  `patient_address` varchar(255) NOT NULL,
  `patient_emergency_contact` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` (`patient_id`, `patient_first_name`, `patient_last_name`, `patient_date_of_birth`, `patient_gender`, `patient_phone_number`, `patient_email`, `patient_address`, `patient_emergency_contact`) VALUES
(1, 'Michael', 'Scott', '1964-03-15', 'Male', '+123-456-7890', 'michael.scott@dundermifflin.com', 'Scranton, PA', 'Jim Halpert'),
(2, 'Pam', 'Beesly', '1979-03-25', 'Female', '234-567-8901', 'pam.beesly@dundermifflin.com', 'Scranton, PA', 'Jim Halpert'),
(3, 'Jim', 'Halpert', '1978-10-01', 'Male', '345-678-9012', 'jim.halpert@dundermifflin.com', 'Scranton, PA', 'Pam Beesly'),
(4, 'Dwight', 'Schrute', '1970-01-20', 'Male', '456-789-0123', 'dwight.schrute@dundermifflin.com', 'Scranton, PA', 'Michael Scott'),
(5, 'Angela', 'Martin', '1971-06-25', 'Female', '567-890-1234', 'angela.martin@dundermifflin.com', 'Scranton, PA', 'Oscar Martinez');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staff_id` int(11) NOT NULL,
  `staff_first_name` varchar(50) NOT NULL,
  `staff_last_name` varchar(50) NOT NULL,
  `staff_role` enum('Doctor','Nurse','Receptionist','Administrator') NOT NULL,
  `staff_phone_number` varchar(20) NOT NULL,
  `staff_email` varchar(100) NOT NULL,
  `staff_address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `staff_first_name`, `staff_last_name`, `staff_role`, `staff_phone_number`, `staff_email`, `staff_address`) VALUES
(0, 'Admin', 'Adminovic', 'Administrator', '00001111', 'admin@gmail.com', 'Admin\'s St, 1-1'),
(1, 'Jan', 'Levinson', 'Doctor', '678-901-2345', 'jan.levinson@medicalcenter.com', 'Scranton, PA'),
(2, 'Stanley', 'Hudson', 'Doctor', '789-012-3456', 'stanley.hudson@medicalcenter.com', 'Scranton, PA'),
(3, 'Kelly', 'Kapoor', 'Receptionist', '890-123-4567', 'kelly.kapoor@medicalcenter.com', 'Scranton, PA'),
(4, 'Toby', 'Flenderson', 'Administrator', '901-234-5678', 'toby.flenderson@medicalcenter.com', 'Scranton, PA'),
(5, 'Oscar', 'Martinez', 'Doctor', '012-345-6789', 'oscar.martinez@medicalcenter.com', 'Scranton, PA'),
(7, 'Polina', 'Korepanova', 'Administrator', '1234567', 'polkorams@gmail.com', 'Belgrade, Serbia'),
(8, 'John', 'Doe', 'Doctor', '111-222-3333', 'doctor@gmail.com', '123 Main St'),
(9, 'Jane', 'Smith', 'Doctor', '444-555-6666', 'doctor2@gmail.com', '456 Elm St');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `staff_id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `staff_role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
  ADD PRIMARY KEY (`appointment_id`),
  ADD KEY `appointments_ibfk_1` (`appointment_patient_id`),
  ADD KEY `appointments_ibfk_2` (`appointment_doctor_id`);

--
-- Indexes for table `billing`
--
ALTER TABLE `billing`
  ADD PRIMARY KEY (`bill_id`),
  ADD KEY `billing_ibfk_1` (`bill_appointment_id`);

--
-- Indexes for table `medicalrecords`
--
ALTER TABLE `medicalrecords`
  ADD PRIMARY KEY (`record_id`),
  ADD KEY `fk_medicalrecords_appointments` (`record_appointment_id`);

--
-- Indexes for table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`patient_id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staff_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`staff_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointments`
--
ALTER TABLE `appointments`
  MODIFY `appointment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `billing`
--
ALTER TABLE `billing`
  MODIFY `bill_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `medicalrecords`
--
ALTER TABLE `medicalrecords`
  MODIFY `record_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `patients`
--
ALTER TABLE `patients`
  MODIFY `patient_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `staff_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointments`
--
ALTER TABLE `appointments`
  ADD CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`appointment_patient_id`) REFERENCES `patients` (`patient_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`appointment_doctor_id`) REFERENCES `staff` (`staff_id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `billing`
--
ALTER TABLE `billing`
  ADD CONSTRAINT `billing_ibfk_1` FOREIGN KEY (`bill_appointment_id`) REFERENCES `appointments` (`appointment_id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `medicalrecords`
--
ALTER TABLE `medicalrecords`
  ADD CONSTRAINT `fk_medicalrecords_appointments` FOREIGN KEY (`record_appointment_id`) REFERENCES `appointments` (`appointment_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
