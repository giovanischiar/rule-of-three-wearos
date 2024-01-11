package io.schiar.threerule.model

data class Numbers(var a: Double? = null, var b: Double? = null, var c: Double? = null) {
    fun calculateD(): Double? {
        val a = a ?: return null
        val b = b ?: return null
        val c = c ?: return null
        if (a == 0.0) return null
        return (c * b) / a
    }
}