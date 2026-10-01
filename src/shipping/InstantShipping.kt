import kotlin.math.ceil

// Instant: jarak 0 - 15 km, tiba dalam hitungan jam
class InstantShipping : ShippingMethod {
    override val name = "Instant"
    override val minDistanceKm = 0.0
    override val maxDistanceKm = 15.0
    override val estimation = "1-3 jam"

    override fun calculateCost(distanceKm: Double): Double {
        return 10_000.0 + ceil(distanceKm) * 2_500.0  // Rp 10.000 + Rp 2.500/km
    }
}
