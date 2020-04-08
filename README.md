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
   
## Implementações Futuras

- Quantidade de agendamentos mensais por Plano
- Entidade MonthlySchedule para controlar quantidade de agendamentos
- Rotina na MonthlySchedule para zerar quantidade de agendamentos todo dia 1 de cada mês
- Validações em agendamentos (data máxima para agendar e cancelar)
- Validação de sessão com tokens
- Dividir aplicação entre front-end e back-end para web
- Desenvolver o app mobile para os clients e app web para os providers
