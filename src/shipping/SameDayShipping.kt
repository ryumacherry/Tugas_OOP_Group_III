import kotlin.math.ceil

// Same Day: jarak 0 - 40 km, tiba di hari yang sama
class SameDayShipping : ShippingMethod {
    override val name = "Same Day"
    override val minDistanceKm = 0.0
    override val maxDistanceKm = 40.0
    override val estimation = "6-12 jam (hari yang sama)"

    override fun calculateCost(distanceKm: Double): Double {
        return 8_000.0 + ceil(distanceKm) * 1_500.0  // Rp 8.000 + Rp 1.500/km
    }
}
