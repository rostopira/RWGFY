package dev.rostopira.rwgfy

import androidx.annotation.StringRes
import dev.rostopira.rwgfy.shared.R
import rip.russianwarship.model.StatisticsObject

enum class StatsField(
    val getterTotal: (StatisticsObject) -> Long,
    val getterIncrease: (StatisticsObject) -> Long?,
    @StringRes val descriptionId: Int,
    @StringRes private val descrShortId: Int? = null,
) {
    Personnel(
        { it.stats.personnelUnits },
        { it.increase?.personnelUnits },
        R.string.sf_personnel_units,
        R.string.sfs_personnel_units,
    ),
    Tanks(
        { it.stats.tanks },
        { it.increase?.tanks },
        R.string.sf_tanks,
    ),
    AFV(
        { it.stats.armouredFightingVehicles },
        { it.increase?.armouredFightingVehicles },
        R.string.sf_armoured_fighting_vehicles,
        R.string.sfs_armoured_fighting_vehicles,
    ),
    Artillery(
        { it.stats.artillerySystems },
        { it.increase?.artillerySystems},
        R.string.sf_artillery_systems,
        R.string.sfs_artillery_systems,
    ),
    MLRS(
        { it.stats.mlrs },
        { it.increase?.mlrs },
        R.string.sf_mlrs,
        R.string.sfs_mlrs,
    ),
    AAW(
        { it.stats.aaWarfareSystems },
        { it.increase?.aaWarfareSystems },
        R.string.sf_aa_warfare_systems,
        R.string.sfs_aa_warfare_systems,
    ),
    Planes(
        { it.stats.planes },
        { it.increase?.planes },
        R.string.sf_planes,
    ),
    Helicopters(
        { it.stats.helicopters },
        { it.increase?.helicopters },
        R.string.sf_helicopters,
    ),
    Vehicles(
        { it.stats.vehiclesFuelTanks },
        { it.increase?.vehiclesFuelTanks },
        R.string.sf_vehicles_fuel_tanks,
        R.string.sfs_vehicles_fuel_tanks,
    ),
    Warships(
        { it.stats.warshipsCutters },
        { it.increase?.warshipsCutters },
        R.string.sf_warships_cutters,
        R.string.sfs_warships_cutters,
    ),
    UAV(
        { it.stats.uavSystems },
        { it.increase?.uavSystems },
        R.string.sf_uav_systems,
        R.string.sfs_uav_systems,
    ),
    SpecialEquipment(
        { it.stats.specialMilitaryEquip },
        { it.increase?.specialMilitaryEquip },
        R.string.sf_special_military_equip,
        R.string.sfs_special_military_equip,
    ),
    ATGMandSRBM(
        { it.stats.atgmSrbmSystems },
        { it.increase?.atgmSrbmSystems },
        R.string.sf_atgm_srbm_systems,
        R.string.sfs_atgm_srbm_systems,
    ),
    CruiseMissiles(
        { it.stats.cruiseMissiles },
        { it.increase?.cruiseMissiles },
        R.string.sf_cruise_missiles,
    );

    @get:StringRes
    val shortDescriptionId: Int get() =
        descrShortId ?: descriptionId
}