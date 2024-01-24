package com.example.guidespringbatch.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Customer(
    firstName: String,
    middleName: String,
    lastName: String,
    ssn: String,
    emailAddress: String,
    homePhone: String,
    cellPhone: String,
    workPhone: String,
    notificationPref: String
) : BaseEntity() {
    @Column(length = 45)
    var firstName: String = firstName
        protected set

    @Column(length = 45)
    var middleName: String = middleName
        protected set

    @Column(length = 45)
    var lastName: String = lastName
        protected set

    @Column(length = 45)
    var ssn: String = ssn
        protected set

    @Column(length = 255)
    var emailAddress: String = emailAddress
        protected set

    @Column(length = 10)
    var homePhone: String = homePhone
        protected set

    @Column(length = 10)
    var cellPhone: String = cellPhone
        protected set

    @Column(length = 10)
    var workPhone: String = workPhone
        protected set

    @Column(length = 1)
    var notificationPref: String = notificationPref
        protected set
}
