package dev.vozniack.securo.core.domain.entity

interface RelatedPrivilege {

    var privilege: Privilege

    var excluded: Boolean
}
