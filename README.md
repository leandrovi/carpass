# Arquitetura Interna da Aplicação

## Stack Utilizada

- Java SE
- MySQL + JDBC
- Java FX

## Estrutura de pastas

application | db | gui | model

    ./
    ├── application
    │   ├── Main.java
    │   └── application.css
    ├── db
    │   ├── DB.java
    │   ├── DbException.java
    │   └── DbIntegrityException.jsvs
    ├── gui
    │   ├── About.fxml
    │   ├── AppointmentForm.fxml
    │   ├── AppointmentForm.fxml
    │   └── ...
    └── model
        ├── dao
        │   ├── DaoFactory.java
        │   ├── AppointmentDao.java
        │   ├── CustomerDao.java
        │   ├── PlanDao.java
        │   ├── ProviderDao.java
        │   ├── RatingDao.java
        │   ├── SpecialtyDao.java
        │   ├── VehicleDao.java
        │   └── impl
        │       ├── AppointmentDaoJDBC.java
        │       ├── CustomerDaoJDBC.java
        │       ├── PlanDaoJDBC.java
        │       ├── ProviderDaoJDBC.java
        │       ├── RatingDaoJDBC.java
        │       ├── SpecialtyDaoJDBC.java
        │       └── AppointmentFormController.java
        └── entities
        │   ├── Appointment.java
        │   ├── Customer.java
        │   ├── Plan.java
        │   ├── Povider.java
        │   ├── Rating.java
        │   ├── Specialty.java
        │   └── Vechicle.java
        └── exceptions
        │   └── ValidationException.java
        └── services
            ├── AppointmentService.java
            ├── CustomerService.java
            ├── PlanService.java
            ├── ProviderService.java
            ├── RatingService.java
            ├── SpecialtyService.java
            └── VehicleService.java
    
