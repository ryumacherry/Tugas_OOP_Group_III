interface ShippingMethod {
    val name: String
    val minDistanceKm: Double
    val maxDistanceKm: Double
    val estimation: String

    fun calculateCost(distanceKm: Double): Double

    fun isAvailable(distanceKm: Double): Boolean {
        return distanceKm in minDistanceKm..maxDistanceKm
    }
}

object ShippingOptions {
    val all: List<ShippingMethod> = listOf(InstantShipping(), SameDayShipping())

    fun availableFor(distanceKm: Double): List<ShippingMethod> {
        return all.filter { it.isAvailable(distanceKm) }
    }
}
