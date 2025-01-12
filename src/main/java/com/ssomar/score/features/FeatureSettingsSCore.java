package com.ssomar.score.features;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.config.Locale;
import com.ssomar.score.features.lang.*;
import com.ssomar.score.utils.logging.Utils;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

public enum FeatureSettingsSCore implements FeatureSettingsInterface {

    HARDNESS(getFeatureSettings("HARDNESS")),
    SPROJECTILE(getFeatureSettings("SPROJECTILE")),
    VARIABLE(getFeatureSettings("VARIABLE")),
    aboveValue(getFeatureSettings("aboveValue")),
    activator(getFeatureSettings("activator")),
    activators(getFeatureSettings("activators")),
    activeTitle(getFeatureSettings("activeTitle", true)),
    playerCanSit(getFeatureSettings("playerCanSit")),
    amount(getFeatureSettings("amount")),
    amplifier(getFeatureSettings("amplifier")),
    armorTrim(getFeatureSettings("armorTrim")),
    aroundBlock(getFeatureSettings("aroundBlock","AroundBlock")),
    aroundBlockCdts(getFeatureSettings("aroundBlockCdts","blockAroundCdts")),
    attribute(getFeatureSettings("attribute")),
    attributes(getFeatureSettings("attributes")),
    bannerSettings(getFeatureSettings("bannerSettings")),
    blockType(getFeatureSettings("blockType")),
    blockTypeMustBe(getFeatureSettings("blockTypeMustBe")),
    blocks(getFeatureSettings("blocks")),
    bounce(getFeatureSettings("bounce")),
    cancelActionEnchantInAnvil(getFeatureSettings("cancelActionEnchantInAnvil","cancel-enchant-anvil")),
    cancelActionRenameInAnvil(getFeatureSettings("cancelActionRenameInAnvil","cancel-rename-anvil")),
    cancelAllCraft(getFeatureSettings("cancelAllCraft", "cancel-item-craft")),
    cancelAnvil(getFeatureSettings("cancelAnvil","cancel-anvil")),
    cancelArmorStand(getFeatureSettings("cancelArmorStand","cancel-armorstand")),
    cancelBeacon(getFeatureSettings("cancelBeacon","cancel-beacon")),
    cancelBrewing(getFeatureSettings("cancelBrewing","cancel-brewing")),
    cancelCartography(getFeatureSettings("cancelCartography","cancel-cartography")),
    cancelComposter(getFeatureSettings("cancelComposter","cancel-composter")),
    cancelConsumption(getFeatureSettings("cancelConsumption","cancel-consumption")),
    cancelCraft(getFeatureSettings("cancelCraft","cancel-item-craft-no-custom")),
    cancelDepositInChest(getFeatureSettings("cancelDepositInChest","cancel-deposit-in-chest")),
    cancelDepositInFurnace(getFeatureSettings("cancelDepositInFurnace","cancel-deposit-in-furnace")),
    cancelDispenser(getFeatureSettings("cancelDispenser","cancel-dispenser")),
    cancelDropper(getFeatureSettings("cancelDropper","cancel-dropper")),
    cancelEnchant(getFeatureSettings("cancelEnchant","cancel-enchant")),
    cancelEventIfError(getFeatureSettings("cancelEventIfError")),
    cancelEventIfInCooldown(getFeatureSettings("cancelEventIfInCooldown")),
    cancelEventIfMaxReached(getFeatureSettings("cancelEventIfMaxReached")),
    cancelEventIfNoPermission(getFeatureSettings("cancelEventIfNoPermission")),
    cancelEventIfNotValid(getFeatureSettings("cancelEventIfNotValid")),
    cancelEvents(getFeatureSettings("cancelEvents")),
    cancelGrindStone(getFeatureSettings("cancelGrindStone","cancel-grind-stone")),
    cancelHopper(getFeatureSettings("cancelHopper","cancel-hopper")),
    cancelHorn(getFeatureSettings("cancelHorn","cancel-horn")),
    cancelHorse(getFeatureSettings("cancelHorse","cancel-horse")),
    cancelItemBurn(getFeatureSettings("cancelItemBurn","cancel-item-burn")),
    cancelItemDeleteByCactus(getFeatureSettings("cancelItemDeleteByCactus","cancel-item-delete-by-cactus")),
    cancelItemDeleteByLightning(getFeatureSettings("cancelItemDeleteByLightning","cancel-item-delete-by-lightning")),
    cancelItemDrop(getFeatureSettings("cancelItemDrop","cancel-item-drop")),
    cancelItemFrame(getFeatureSettings("cancelItemFrame","cancel-item-frame")),
    cancelItemPlace(getFeatureSettings("cancelItemPlace","cancel-item-place")),
    cancelLectern(getFeatureSettings("cancelLectern","cancel-lectern")),
    cancelLoom(getFeatureSettings("cancelLoom","cancel-loom")),
    cancelDecoratedPot(getFeatureSettings("cancelDecoratedPot","cancel-decorated-pot")),
    cancelCrafter(getFeatureSettings("cancelCrafter","cancel-crafter")),
    cancelMerchant(getFeatureSettings("cancelMerchant","cancel-merchant")),
    cancelSmithingTable(getFeatureSettings("cancelSmithingTable","cancel-smithing-table")),
    cancelStoneCutter(getFeatureSettings("cancelStoneCutter","cancel-stone-cutter")),
    cancelSwapHand(getFeatureSettings("cancelSwapHand","cancel-swap-hand")),
    cancelToolInteractions(getFeatureSettings("cancelToolInteractions","cancel-tool-interactions")),
    charged(getFeatureSettings("charged")),
    color(getFeatureSettings("color")),
    containerContent(getFeatureSettings("containerContent")),
    colors(getFeatureSettings("colors")),
    comparator(getFeatureSettings("comparator")),
    cooldown(getFeatureSettings("cooldown")),
    cooldownMsg(getFeatureSettings("cooldownMsg")),
    critical(getFeatureSettings("critical")),
    customModelData(getFeatureSettings("customModelData")),
    customName(getFeatureSettings("customName")),
    customNameVisible(getFeatureSettings("customNameVisible")),
    damage(getFeatureSettings("damage")),
    default_double(getFeatureSettings("default_double","default")),
    default_list(getFeatureSettings("default_list","default")),
    default_string(getFeatureSettings("default_string","default")),
    delay(getFeatureSettings("delay")),
    delayInTick(getFeatureSettings("delayInTick")),
    despawnDelay(getFeatureSettings("despawnDelay")),
    detailedBlocks(getFeatureSettings("detailedBlocks")),
    detailedEffects(getFeatureSettings("detailedEffects")),
    detailedItems(getFeatureSettings("detailedItems")),
    detailedSlots(getFeatureSettings("detailedSlots")),
    displayCooldownMessage(getFeatureSettings("displayCooldownMessage")),
    displayNameDrop(getFeatureSettings("displayNameDrop")),
    dropOptions(getFeatureSettings("dropOptions")),
    duration(getFeatureSettings("duration")),
    eastValue(getFeatureSettings("eastValue")),
    effects(getFeatureSettings("effects")),
    enableArmorTrim(getFeatureSettings("enableArmorTrim")),
    enchantment(getFeatureSettings("enchantment")),
    enchantmentWithLevel(getFeatureSettings("enchantmentWithLevel")),
    enchantments(getFeatureSettings("enchantments")),
    entityType(getFeatureSettings("entityType")),
    errorMessage(getFeatureSettings("errorMessage")),
    errorMsg(getFeatureSettings("errorMsg")),
    executableItem(getFeatureSettings("executableItem")),
    itemsAdder(getFeatureSettings("itemsAdder")),
    fadeColors(getFeatureSettings("fadeColors")),
    fireworkFeatures(getFeatureSettings("fireworkFeatures")),
    fireworkExplosion(getFeatureSettings("fireworkExplosion")),
    fireworkExplosions(getFeatureSettings("fireworkExplosions")),
    hasTrail(getFeatureSettings("hasTrail")),
    hasTwinkle(getFeatureSettings("hasTwinkle")),
    for_(getFeatureSettings("for_","for")),
    giveFirstJoin(getFeatureSettings("giveFirstJoin")),
    giveFirstJoinAmount(getFeatureSettings("giveFirstJoinAmount")),
    giveFirstJoinFeatures(getFeatureSettings("giveFirstJoin")),
    giveFirstJoinSlot(getFeatureSettings("giveFirstJoinSlot")),
    glowDrop(getFeatureSettings("glowDrop")),
    glowDropColor(getFeatureSettings("glowDropColor", true)),
    glowing(getFeatureSettings("glowing")),
    gravity(getFeatureSettings("gravity")),
    hasExecutableItem(getFeatureSettings("hasExecutableItem")),
    hasIcon(getFeatureSettings("hasIcon")),
    hasItem(getFeatureSettings("hasItem")),
    hasParticles(getFeatureSettings("hasParticles")),
    headDBID(getFeatureSettings("headDBID")),
    headFeatures(getFeatureSettings("headFeatures")),
    headValue(getFeatureSettings("headValue")),
    hideArmorTrim(getFeatureSettings("hideArmorTrim")),
    hideAttributes(getFeatureSettings("hideAttributes")),
    hideDestroys(getFeatureSettings("hideDestroys")),
    hideDye(getFeatureSettings("hideDye")),
    hideEnchantments(getFeatureSettings("hideEnchantments")),
    hidePlacedOn(getFeatureSettings("hidePlacedOn")),
    hideAdditionalTooltip(getFeatureSettings("hideAdditionalTooltip")),
    //TODO find a way to fix this typo and accept both
    hideTooltip(getFeatureSettings("hideTooltip","hideToolTip")),
    hidePotionEffects(getFeatureSettings("hidePotionEffects")),
    hideUnbreakable(getFeatureSettings("hideUnbreakable")),
    hideUsage(getFeatureSettings("hideUsage")),
    hiders(getFeatureSettings("hiders")),
    icon(getFeatureSettings("icon")),
    ifAdult(getFeatureSettings("ifAdult")),
    ifBaby(getFeatureSettings("ifBaby")),
    ifBlockAge(getFeatureSettings("ifBlockAge")),
    ifBlockLocationX(getFeatureSettings("ifBlockLocationX")),
    ifBlockLocationY(getFeatureSettings("ifBlockLocationY")),
    ifBlockLocationZ(getFeatureSettings("ifBlockLocationZ")),
    ifBlocking(getFeatureSettings("ifBlocking")),
    ifCanBreakTargetedBlock(getFeatureSettings("ifCanBreakTargetedBlock")),
    ifContainerContains(getFeatureSettings("ifContainerContains")),
    ifContainerContainsEI(getFeatureSettings("ifContainerContainsEI")),
    ifContainerContainsSellableItems(getFeatureSettings("ifContainerContainsSellableItems")),
    ifContainerEmpty(getFeatureSettings("ifContainerEmpty")),
    ifContainerNotEmpty(getFeatureSettings("ifContainerNotEmpty")),
    ifCrossbowMustBeCharged(getFeatureSettings("ifCrossbowMustBeCharged")),
    ifCrossbowMustNotBeCharged(getFeatureSettings("ifCrossbowMustNotBeCharged")),
    ifCursorDistance(getFeatureSettings("ifCursorDistance")),
    ifDurability(getFeatureSettings("ifDurability")),
    ifEntityHealth(getFeatureSettings("ifEntityHealth")),
    ifFlying(getFeatureSettings("ifFlying")),
    ifFromSpawner(getFeatureSettings("ifFromSpawner")),
    ifFrozen(getFeatureSettings("ifFrozen")),
    ifGliding(getFeatureSettings("ifGliding")),
    ifGlowing(getFeatureSettings("ifGlowing")),
    ifHasAI(getFeatureSettings("ifHasAI")),
    ifHasEnchant(getFeatureSettings("ifHasEnchant")),
    ifHasExecutableItems(getFeatureSettings("ifHasExecutableItems")),
    ifHasItems(getFeatureSettings("ifHasItems")),
    ifHasNotEnchant(getFeatureSettings("ifHasNotEnchant")),
    ifHasNotExecutableItems(getFeatureSettings("ifHasNotExecutableItems")),
    ifHasNotItems(getFeatureSettings("ifHasNotItems")),
    ifHasPermission(getFeatureSettings("ifHasPermission")),
    ifHasSaddle(getFeatureSettings("ifHasSaddle")),
    ifHasTag(getFeatureSettings("ifHasTag")),
    ifInBiome(getFeatureSettings("ifInBiome")),
    ifInRegion(getFeatureSettings("ifInRegion")),
    ifInWorld(getFeatureSettings("ifInWorld")),
    ifInvulnerable(getFeatureSettings("ifInvulnerable")),
    ifIsInTheAir(getFeatureSettings("ifIsInTheAir")),
    ifIsInTheBlock(getFeatureSettings("ifIsInTheBlock")),
    ifIsNotInTheAir(getFeatureSettings("ifIsNotInTheAir")),
    ifIsNotInTheBlock(getFeatureSettings("ifIsNotInTheBlock")),
    ifIsNotOnFire(getFeatureSettings("ifIsNotOnFire")),
    ifIsNotOnTheBlock(getFeatureSettings("ifIsNotOnTheBlock")),
    ifIsOnFire(getFeatureSettings("ifIsOnFire")),
    ifIsOnTheBlock(getFeatureSettings("ifIsOnTheBlock")),
    ifIsPowered(getFeatureSettings("ifIsPowered")),
    ifLightLevel(getFeatureSettings("ifLightLevel")),
    ifMustBeNatural(getFeatureSettings("ifMustBeNatural")),
    ifMustBeNotNatural(getFeatureSettings("ifMustBeNotNatural")),
    ifMustBeNotPowered(getFeatureSettings("ifMustBeNotPowered")),
    ifName(getFeatureSettings("ifName")),
    ifNamed(getFeatureSettings("ifNamed")),
    ifNeedPlayerConfirmation(getFeatureSettings("ifNeedPlayerConfirmation")),
    ifNoPlayerMustBeOnTheBlock(getFeatureSettings("ifNoPlayerMustBeOnTheBlock")),
    ifNotBlocking(getFeatureSettings("ifNotBlocking")),
    ifNotEntityType(getFeatureSettings("ifNotEntityType")),
    ifNotFlying(getFeatureSettings("ifNotFlying")),
    ifNotFromSpawner(getFeatureSettings("ifNotFromSpawner")),
    ifNotGliding(getFeatureSettings("ifNotGliding")),
    ifNotHasAI(getFeatureSettings("ifNotHasAI")),
    ifNotHasPermission(getFeatureSettings("ifNotHasPermission")),
    ifNotHasSaddle(getFeatureSettings("ifNotHasSaddle")),
    ifNotHasTag(getFeatureSettings("ifNotHasTag")),
    ifNotInBiome(getFeatureSettings("ifNotInBiome")),
    ifNotInRegion(getFeatureSettings("ifNotInRegion")),
    ifNotInWorld(getFeatureSettings("ifNotInWorld")),
    ifNotNamed(getFeatureSettings("ifNotNamed")),
    ifNotOwnerOfTheEI(getFeatureSettings("ifNotOwnerOfTheEI")),
    ifNotSneaking(getFeatureSettings("ifNotSneaking")),
    ifNotSprinting(getFeatureSettings("ifNotSprinting")),
    ifNotStunned(getFeatureSettings("ifNotStunned")),
    ifNotSwimming(getFeatureSettings("ifNotSwimming")),
    ifNotTamed(getFeatureSettings("ifNotTamed")),
    ifNotTargetBlock(getFeatureSettings("ifNotTargetBlock")),
    ifOnFire(getFeatureSettings("ifOnFire")),
    ifOwnerOfTheEI(getFeatureSettings("ifOwnerOfTheEI")),
    ifPlantFullyGrown(getFeatureSettings("ifPlantFullyGrown")),
    ifPlantNotFullyGrown(getFeatureSettings("ifPlantNotFullyGrown")),
    ifPlayerEXP(getFeatureSettings("ifPlayerEXP")),
    ifPlayerFoodLevel(getFeatureSettings("ifPlayerFoodLevel")),
    ifPlayerHasEffect(getFeatureSettings("ifPlayerHasEffect")),
    ifPlayerHasEffectEquals(getFeatureSettings("ifPlayerHasEffectEquals")),
    ifPlayerHealth(getFeatureSettings("ifPlayerHealth")),
    ifPlayerLevel(getFeatureSettings("ifPlayerLevel")),
    ifPlayerMounts(getFeatureSettings("ifPlayerMounts")),
    ifPlayerMustBeInHisTown(getFeatureSettings("ifPlayerMustBeInHisTown")),
    ifPlayerMustBeOnHisClaim(getFeatureSettings("ifPlayerMustBeOnHisClaim")),
    ifPlayerMustBeOnHisClaimOrWilderness(getFeatureSettings("ifPlayerMustBeOnHisClaimOrWilderness")),
    ifPlayerMustBeOnHisIsland(getFeatureSettings("ifPlayerMustBeOnHisIsland")),
    ifPlayerMustBeOnHisPlot(getFeatureSettings("ifPlayerMustBeOnHisPlot")),
    ifPlayerMustBeOnTheBlock(getFeatureSettings("ifPlayerMustBeOnTheBlock")),
    ifPlayerNotHasEffect(getFeatureSettings("ifPlayerNotHasEffect")),
    ifPlayerNotMounts(getFeatureSettings("ifPlayerNotMounts")),
    ifPosX(getFeatureSettings("ifPosX")),
    ifPosY(getFeatureSettings("ifPosY")),
    ifPosZ(getFeatureSettings("ifPosZ")),
    ifPowered(getFeatureSettings("ifPowered")),
    ifSneaking(getFeatureSettings("ifSneaking")),
    ifSprinting(getFeatureSettings("ifSprinting")),
    ifStunned(getFeatureSettings("ifStunned")),
    ifSwimming(getFeatureSettings("ifSwimming")),
    ifTamed(getFeatureSettings("ifTamed")),
    ifTargetBlock(getFeatureSettings("ifTargetBlock")),
    ifUsage(getFeatureSettings("ifUsage")),
    ifWeather(getFeatureSettings("ifWeather")),
    ifWorldTime(getFeatureSettings("ifWorldTime")),
    incendiary(getFeatureSettings("incendiary")),
    invisible(getFeatureSettings("invisible")),
    isAmbient(getFeatureSettings("isAmbient")),
    isCooldownInTicks(getFeatureSettings("isCooldownInTicks")),
    items(getFeatureSettings("items")),
    knockbackStrength(getFeatureSettings("knockbackStrength")),
    level(getFeatureSettings("level")),
    lifeTime(getFeatureSettings("lifeTime")),
    lockedInventory(getFeatureSettings("lockedInventory","locked-in-inventory")),
    loop(getFeatureSettings("loop")),
    magicID(getFeatureSettings("magicID")),
    material(getFeatureSettings("material")),
    materialAndTags(getFeatureSettings("materialAndTags")),
    maxUsePerDay(getFeatureSettings("maxUsePerDay", true)),
    messageIfMaxReached(getFeatureSettings("messageIfMaxReached")),
    messageIfNotValid(getFeatureSettings("messageIfNotValid")),
    messageIfNotValidForTarget(getFeatureSettings("messageIfNotValidForTarget")),
    modification_double(getFeatureSettings("modification_double","modification")),
    modification_string(getFeatureSettings("modification_string","modification")),
    multiChoices(getFeatureSettings("multiChoices","multi-choices")),
    name(getFeatureSettings("name")),
    northValue(getFeatureSettings("northValue")),
    notExecutableItem(getFeatureSettings("notExecutableItem")),
    object(getFeatureSettings("object")),
    operation(getFeatureSettings("operation")),
    part1(getFeatureSettings("part1")),
    part2(getFeatureSettings("part2")),
    particle(getFeatureSettings("particle")),
    particles(getFeatureSettings("particles")),
    particlesAmount(getFeatureSettings("particlesAmount")),
    particlesDelay(getFeatureSettings("particlesDelay")),
    particlesDensity(getFeatureSettings("particlesDensity")),
    particlesOffSet(getFeatureSettings("particlesOffSet")),
    particlesSpeed(getFeatureSettings("particlesSpeed")),
    particlesType(getFeatureSettings("particlesType")),
    pattern(getFeatureSettings("pattern")),
    patterns(getFeatureSettings("patterns")),
    pausePlaceholdersConditions(getFeatureSettings("pausePlaceholdersConditions")),
    pauseWhenOffline(getFeatureSettings("pauseWhenOffline")),
    enableVisualCooldown(getFeatureSettings("enableVisualCooldown")),
    period(getFeatureSettings("period")),
    periodInTicks(getFeatureSettings("periodInTicks")),
    pickupStatus(getFeatureSettings("pickupStatus")),
    pierceLevel(getFeatureSettings("pierceLevel")),
    placeholderCondition(getFeatureSettings("placeholderCondition")),
    placeholderConditionCmds(getFeatureSettings("placeholderConditionCmds")),
    placeholdersConditions(getFeatureSettings("placeholdersConditions")),
    potionColor(getFeatureSettings("potionColor")),
    potionEffect(getFeatureSettings("potionEffect")),
    potionEffectType(getFeatureSettings("potionEffectType")),
    potionEffects(getFeatureSettings("potionEffects")),
    potionExtended(getFeatureSettings("potionExtended")),
    potionSettings(getFeatureSettings("potionSettings")),
    potionType(getFeatureSettings("potionType")),
    potionUpgraded(getFeatureSettings("potionUpgraded")),
    radius(getFeatureSettings("radius")),
    redstoneColor(getFeatureSettings("redstoneColor")),
    removeWhenHitBlock(getFeatureSettings("removeWhenHitBlock")),
    requiredExecutableItem(getFeatureSettings("requiredExecutableItem", true)),
    requiredExecutableItems(getFeatureSettings("requiredExecutableItems", true)),
    requiredExperience(getFeatureSettings("requiredExperience", true)),
    requiredGroups(getFeatureSettings("requiredGroups")),
    requiredItem(getFeatureSettings("requiredItem", false)),
    requiredItems(getFeatureSettings("requiredItems", true)),
    requiredLevel(getFeatureSettings("requiredLevel",true)),
    requiredMagic(getFeatureSettings("requiredMagic",true)),
    requiredMagics(getFeatureSettings("requiredMagics", true)),
    requiredMana(getFeatureSettings("requiredMana", true)),
    requiredMoney(getFeatureSettings("requiredMoney", true)),
    restrictions(getFeatureSettings("restrictions")),
    silent(getFeatureSettings("silent")),
    slot(getFeatureSettings("slot")),
    southValue(getFeatureSettings("southValue")),
    stopCheckingOtherConditionsIfNotValid(getFeatureSettings("stopCheckingOtherConditionsIfNotValid")),
    string(getFeatureSettings("string")),
    subPattern(getFeatureSettings("subPattern")),
    subPatterns(getFeatureSettings("subPatterns")),
    tags(getFeatureSettings("tags")),
    title(getFeatureSettings("title")),
    titleAdjustement(getFeatureSettings("titleAdjustement")),
    titleOptions(getFeatureSettings("titleOptions", true)),
    sitFeatures(getFeatureSettings("sitFeatures")),
    storageFeatures(getFeatureSettings("storageFeatures")),
    trimMaterial(getFeatureSettings("trimMaterial")),
    type(getFeatureSettings("type")),
    underValue(getFeatureSettings("underValue")),
    usageConditions(getFeatureSettings("usageConditions")),
    usePerDay(getFeatureSettings("usePerDay", true)),
    uuid(getFeatureSettings("uuid")),
    variable(getFeatureSettings("variable")),
    variableName(getFeatureSettings("variableName")),
    variableUpdate(getFeatureSettings("variableUpdate")),
    variables(getFeatureSettings("variables")),
    variablesModification(getFeatureSettings("variablesModification")),
    velocity(getFeatureSettings("velocity")),
    visualFire(getFeatureSettings("visualFire")),
    visualItem(getFeatureSettings("visualItem")),
    westValue(getFeatureSettings("westValue")),
    bookFeatures(getFeatureSettings("bookFeatures")),
    usageFeatures(getFeatureSettings("usageFeatures")),
    myFurnitureFeatures(getFeatureSettings("myFurnitureFeatures")),
    pages(getFeatureSettings("pages")),
    author(getFeatureSettings("author")),
    equippableFeatures(getFeatureSettings("equippableFeatures")),
    enableSound(getFeatureSettings("enableSound")),
    sound(getFeatureSettings("sound")),
    equipModel(getFeatureSettings("equipModel")),
    cameraOverlay(getFeatureSettings("cameraOverlay")),
    damageableOnHurt(getFeatureSettings("damageableOnHurt")),
    dispensable(getFeatureSettings("dispensable")),
    swappable(getFeatureSettings("swappable")),
    allowedEntities(getFeatureSettings("allowedEntities")),
    repairableFeatures(getFeatureSettings("repairableFeatures")),
    repairCost(getFeatureSettings("repairCost")),
    glider(getFeatureSettings("glider")),
    itemModel(getFeatureSettings("itemModel")),
    tooltipModel(getFeatureSettings("tooltipModel")),
    toolRules(getFeatureSettings("toolRules")),
    toolRule(getFeatureSettings("toolRule")),
    miningSpeed(getFeatureSettings("miningSpeed")),
    enable(getFeatureSettings("enable")),
    defaultMiningSpeed(getFeatureSettings("defaultMiningSpeed")),
    correctForDrops(getFeatureSettings("correctForDrops")),
    materials(getFeatureSettings("blocks")),
    damagePerBlock(getFeatureSettings("damagePerBlock")),
    instrumentFeatures(getFeatureSettings("instrumentFeatures")),
    instrument(getFeatureSettings("instrument")),
    chargedProjectiles(getFeatureSettings("chargedProjectiles")),
    hitSound(getFeatureSettings("hitSound")),
    useCooldownFeatures(getFeatureSettings("useCooldownFeatures")),
    cooldownGroup(getFeatureSettings("cooldownGroup")),
    vanillaUseCooldown(getFeatureSettings("vanillaUseCooldown")),
    typeTarget(getFeatureSettings("typeTarget")),
    detailedClick(getFeatureSettings("detailedClick")),
    usageModification(getFeatureSettings("usageModification", true)),
    cancelEvent(getFeatureSettings("cancelEvent")),
    noActivatorRunIfTheEventIsCancelled(getFeatureSettings("noActivatorRunIfTheEventIsCancelled")),
    silenceOutput(getFeatureSettings("silenceOutput")),
    mustBeAProjectileLaunchWithTheSameEI(getFeatureSettings("mustBeAProjectileLaunchWithTheSameEI")),
    desactiveDrops(getFeatureSettings("desactiveDrops")),
    option(getFeatureSettings("option")),
    cooldownOptions(getFeatureSettings("cooldownOptions")),
    globalCooldownOptions(getFeatureSettings("globalCooldownOptions")),
    detailedEntities(getFeatureSettings("detailedEntities")),
    detailedTargetEntities(getFeatureSettings("detailedTargetEntities")),
    detailedTargetBlocks(getFeatureSettings("detailedTargetBlocks")),
    detailedDamageCauses(getFeatureSettings("detailedDamageCauses")),
    detailedCommands(getFeatureSettings("detailedCommands")),
    detailedMessagesContains(getFeatureSettings("detailedMessagesContains")),
    detailedMessagesEquals(getFeatureSettings("detailedMessagesEquals")),
    detailedInventories(getFeatureSettings("detailedInventories")),
    mustBeItsOwnInventory(getFeatureSettings("mustBeItsOwnInventory")),
    commands_player(getFeatureSettings("commands_player","commands")),
    commands_block(getFeatureSettings("commands_block","commands")),
    targetCommands(getFeatureSettings("targetCommands")),
    entityCommands(getFeatureSettings("entityCommands")),
    blockCommands(getFeatureSettings("blockCommands")),
    blockCommands_target_block(getFeatureSettings("blockCommands_target_block","blockCommands")),
    playerCommands(getFeatureSettings("playerCommands")),
    ownerCommands(getFeatureSettings("ownerCommands")),
    consoleCommands(getFeatureSettings("consoleCommands")),
    targetPlayerCommands(getFeatureSettings("targetPlayerCommands")),
    targetEntityCommands(getFeatureSettings("targetEntityCommands")),
    targetBlockCommands(getFeatureSettings("targetBlockCommands")),
    displayCommands(getFeatureSettings("displayCommands")),
    playerConditions(getFeatureSettings("playerConditions")),
    targetConditions(getFeatureSettings("targetConditions")),
    entityConditions(getFeatureSettings("entityConditions")),
    blockConditions(getFeatureSettings("blockConditions")),
    worldConditions(getFeatureSettings("worldConditions")),
    itemConditions(getFeatureSettings("itemConditions")),
    customConditions(getFeatureSettings("customConditions")),
    ownerConditions(getFeatureSettings("ownerConditions")),
    targetBlockConditions(getFeatureSettings("targetBlockConditions")),
    targetPlayerConditions(getFeatureSettings("targetPlayerConditions")),
    targetEntityConditions(getFeatureSettings("targetEntityConditions")),
    displayConditions_conditions(getFeatureSettings("displayConditions_conditions","displayConditions")),
    containsMineInCube(getFeatureSettings("containsMineInCube")),
    playerCooldownOptions(getFeatureSettings("playerCooldownOptions")),
    entityCooldownOptions(getFeatureSettings("entityCooldownOptions")),
    isRefreshableClean(getFeatureSettings("isRefreshableClean")),
    refreshTag(getFeatureSettings("refreshTag","refreshTagDoNotEdit")),
    otherEICooldowns(getFeatureSettings("otherEICooldowns")),
    cooldown_activators(getFeatureSettings("activators")),
    canBeUsedOnlyByTheOwner(getFeatureSettings("canBeUsedOnlyByTheOwner", true)),
    cancelEventIfNotOwner(getFeatureSettings("cancelEventIfNotOwner")),
    onlyOwnerBlackListedActivators(getFeatureSettings("onlyOwnerBlackListedActivators")),
    EXECUTABLEITEM(getFeatureSettings("EXECUTABLEITEM")),
    lore(getFeatureSettings("lore")),
    glow(getFeatureSettings("glow")),
    glowDuration(getFeatureSettings("glowDuration")),
    disableEnchantGlide(getFeatureSettings("disableEnchantGlide")),
    disableStack(getFeatureSettings("disableStack", true)),
    customStackSize(getFeatureSettings("customStackSize", true)),
    keepItemOnDeath(getFeatureSettings("keepItemOnDeath")),
    storeItemInfo(getFeatureSettings("storeItemInfo")),
    keepDefaultAttributes(getFeatureSettings("keepDefaultAttributes")),
    ignoreKeepDefaultAttributesFeature(getFeatureSettings("ignoreKeepDefaultAttributesFeature")),
    unbreakable(getFeatureSettings("unbreakable")),
    usage(getFeatureSettings("usage")),
    usageLimit(getFeatureSettings("usageLimit", true)),
    customModelData_ei(getFeatureSettings("customModelData")),
    whitelistedWorlds(getFeatureSettings("whitelistedWorlds")),
    armorColor(getFeatureSettings("armorColor")),
    fireworkColor(getFeatureSettings("fireworkColor")),
    recognitions(getFeatureSettings("recognitions", true)),
    food(getFeatureSettings("food")),
    nutrition(getFeatureSettings("nutrition")),
    saturation(getFeatureSettings("saturation")),
    isMeat(getFeatureSettings("isMeat")),
    canAlwaysEat(getFeatureSettings("canAlwaysEat")),
    eatSeconds(getFeatureSettings("eatSeconds")),
    blockState(getFeatureSettings("blockState")),
    blockStatePlus(getFeatureSettings("blockStatePlus")),
    bundleContent(getFeatureSettings("bundleContent")),
    itemRarity(getFeatureSettings("itemRarity")),
    rarity(getFeatureSettings("rarity")),
    enableRarity(getFeatureSettings("enableRarity")),
    durability_features(getFeatureSettings("durability")),
    maxDurability(getFeatureSettings("maxDurability")),
    durability(getFeatureSettings("durability")),
    isDurabilityBasedOnUsage(getFeatureSettings("isDurabilityBasedOnUsage")),
    displayConditions(getFeatureSettings("displayConditions")),
    enableFeature(getFeatureSettings("enableFeature")),
    nbt(getFeatureSettings("nbt")),
    autoUpdateFeatures(getFeatureSettings("autoUpdateFeatures")),
    autoUpdateItem(getFeatureSettings("autoUpdateItem")),
    updateMaterial(getFeatureSettings("updateMaterial")),
    updateName(getFeatureSettings("updateName")),
    updateLore(getFeatureSettings("updateLore")),
    updateDurability(getFeatureSettings("updateDurability")),
    updateAttributes(getFeatureSettings("updateAttributes")),
    updateEnchants(getFeatureSettings("updateEnchants")),
    updateCustomModelData(getFeatureSettings("updateCustomModelData")),
    updateArmorSettings(getFeatureSettings("updateArmorSettings")),
    updateHiders(getFeatureSettings("updateHiders")),
    brewingStandFeatures(getFeatureSettings("brewingStandFeatures", true)),
    brewingStandSpeed(getFeatureSettings("brewingStandSpeed", true)),
    boundingBoxZones(getFeatureSettings("boundingBoxZones")),
    boundingBoxZone(getFeatureSettings("boundingBoxZone")),
    from(getFeatureSettings("from")),
    to(getFeatureSettings("to")),
    containerFeatures(getFeatureSettings("containerFeatures")),
    whitelistMaterials(getFeatureSettings("whitelistMaterials")),
    blacklistMaterials(getFeatureSettings("blacklistMaterials")),
    isLocked(getFeatureSettings("isLocked")),
    lockedName(getFeatureSettings("lockedName")),
    inventoryTitle(getFeatureSettings("inventoryTitle")),
    furnaceFeatures(getFeatureSettings("furnaceFeatures", true)),
    furnaceSpeed(getFeatureSettings("furnaceSpeed", true)),
    infiniteFuel(getFeatureSettings("infiniteFuel", true)),
    infiniteVisualLit(getFeatureSettings("infiniteVisualLit", true)),
    fortuneChance(getFeatureSettings("fortuneChance", true)),
    fortuneMultiplier(getFeatureSettings("fortuneMultiplier", true)),
    directionalFeatures(getFeatureSettings("directionalFeatures")),
    forceBlockFaceOnPlace(getFeatureSettings("forceBlockFaceOnPlace")),
    blockFaceOnPlace(getFeatureSettings("blockFaceOnPlace")),
    hopperFeatures(getFeatureSettings("hopperFeatures", true)),
    amountItemsTransferred(getFeatureSettings("amountItemsTransferred", true)),
    chiseledBookshelfFeatures(getFeatureSettings("chiseledBookshelfFeatures")),
    occupiedSlots(getFeatureSettings("occupiedSlots")),
    InteractionZoneFeatures(getFeatureSettings("InteractionZoneFeatures")),
    width(getFeatureSettings("width")),
    height(getFeatureSettings("height")),
    isCollidable(getFeatureSettings("isCollidable")),
    DisplayFeatures(getFeatureSettings("DisplayFeatures")),
    scale(getFeatureSettings("scale")),
    aligned(getFeatureSettings("aligned")),
    customPitch(getFeatureSettings("customPitch")),
    customY(getFeatureSettings("customY")),
    clickToBreak(getFeatureSettings("clickToBreak")),
    EXECUTABLEBLOCK(getFeatureSettings("EXECUTABLEBLOCK")),
    EXECUTABLEBLOCKPLACED(getFeatureSettings("EXECUTABLEBLOCKPLACED")),
    EXECUTABLEEVENT(getFeatureSettings("EXECUTABLEEVENT")),
    FURNITURE(getFeatureSettings("FURNITURE")),
    FURNITUREPLACED(getFeatureSettings("FURNITUREPLACED")),
    creationType(getFeatureSettings("creationType")),
    spawnerType(getFeatureSettings("spawnerType")),
    dropType(getFeatureSettings("dropType")),
    cancelGravity(getFeatureSettings("cancelGravity")),
    cancelLiquidDestroy(getFeatureSettings("cancelLiquidDestroy")),
    onlyBreakableWithEI(getFeatureSettings("onlyBreakableWithEI")),
    dropBlockIfItIsBroken(getFeatureSettings("dropBlockIfItIsBroken")),
    dropBlockWhenItExplodes(getFeatureSettings("dropBlockWhenItExplodes")),
    dropBlockWhenItBurns(getFeatureSettings("dropBlockWhenItBurns")),
    resetInternalDatasWhenBroken(getFeatureSettings("resetInternalDatasWhenBroken")),
    canBeMoved(getFeatureSettings("canBeMoved")),
    itemsAdderID(getFeatureSettings("itemsAdderID")),
    oraxenID(getFeatureSettings("oraxenID")),
    myfurnitureID(getFeatureSettings("myfurnitureID")),
    interactionRange(getFeatureSettings("interactionRange")),
    enabled(getFeatureSettings("enabled")),
    editorIcon(getFeatureSettings("editorIcon")),
    disabledWorlds(getFeatureSettings("disabledWorlds")),
    displayFeatures(getFeatureSettings("displayFeatures")),
    blockLight(getFeatureSettings("blockLight")),
    skyLight(getFeatureSettings("skyLight")),
    glowColor(getFeatureSettings("glowColor")),
    TRADE(getFeatureSettings("TRADE")),
    description(getFeatureSettings("description")),
    delayOfTrade(getFeatureSettings("delayOfTrade")),
    glowWhenTrade(getFeatureSettings("glowWhenTrade")),
    requiredObject(getFeatureSettings("requiredObject")),
    SCREEN(getFeatureSettings("SCREEN")),
    SCREENUNIT(getFeatureSettings("SCREENUNIT")),
    leverUUID(getFeatureSettings("leverUUID")),
    interactionUUID(getFeatureSettings("interactionUUID")),
    rotation(getFeatureSettings("rotation")),
    glowWhenPowered(getFeatureSettings("glowWhenPowered")),
    EXECUTABLELEVER(getFeatureSettings("EXECUTABLELEVER")),
    startDate(getFeatureSettings("startDate")),
    endDate(getFeatureSettings("endDate")),
    when(getFeatureSettings("when")),
    animation(getFeatureSettings("animation")),
    hasConsumeParticles(getFeatureSettings("hasConsumeParticles")),
    consumeSeconds(getFeatureSettings("consumeSeconds")),
    consumableFeatures(getFeatureSettings("consumableFeatures")),
    scheduleFeatures(getFeatureSettings("scheduleFeatures")),
    viewRange(getFeatureSettings("viewRange"));

    private FeatureSettingsInterface settingsInterface;

    FeatureSettingsSCore(FeatureSettingsInterface settingsInterface) {
        this.settingsInterface = settingsInterface;
    }

    public static FeatureSettingsInterface[] getValues() {
        Locale locale = GeneralConfig.getInstance().getLocale();
        switch (locale){
            case FR:
                return FeatureSettingsSCoreFR.values();
            case EN:
                return FeatureSettingsSCoreEN.values();
            case ES:
                return FeatureSettingsSCoreES.values();
            case DE:
                return FeatureSettingsSCoreDE.values();
            case RU:
                return FeatureSettingsSCoreRU.values();
            case ZH:
                return FeatureSettingsSCoreZH.values();
            case ID:
                return FeatureSettingsSCoreID.values();
            case AR:
                return FeatureSettingsSCoreAR.values();
            default:
                return FeatureSettingsSCoreEN.values();
        }
    }

    public static FeatureSettingsInterface getFeatureSettings(String identifier, @Nullable String configName, boolean requirePremium) {
        if(configName == null) configName = identifier;
        FeatureSettingsInterface[] values = getValues();
        for (FeatureSettingsInterface value : values) {
            if (value.getIdentifier().equals(identifier)) {
                value.setName(configName);
                value.setRequirePremium(requirePremium);
                return value;
            }
        }
        Utils.sendConsoleMsg("&c"+SCore.plugin.getNameWithBrackets() + " &cNo feature Translation found for setting: &6" + identifier +" &cin &6"+GeneralConfig.getInstance().getLocale().name());

        /* try in english */
        values = FeatureSettingsSCoreEN.values();
        for (FeatureSettingsInterface value : values) {
            if (value.getIdentifier().equals(identifier)) {
                value.setName(configName);
                value.setRequirePremium(requirePremium);
                return value;
            }
        }
        throw new IllegalArgumentException("No feature settings found for config name: " + identifier);
    }

    public static void reload(){
        for(FeatureSettingsSCore feature : FeatureSettingsSCore.values()){
            feature.settingsInterface = getFeatureSettings(feature.getIdentifier());
        }
    }

    public void setFeatureSettingsInterface(FeatureSettingsInterface featureSettingsInterface){
        this.settingsInterface = featureSettingsInterface;
    }

    public static FeatureSettingsInterface getFeatureSettings(String identifier) {
        return getFeatureSettings(identifier, null);
    }

    public static FeatureSettingsInterface getFeatureSettings(String identifier, @Nullable String configName) {
        return getFeatureSettings(identifier, configName, false);
    }

    public static FeatureSettingsInterface getFeatureSettings(String identifier, boolean requirePremium) {
        return getFeatureSettings(identifier, null, requirePremium);
    }

    @Override
    public String getIdentifier() {
        return this.name();
    }

    @Override
    public String getName() {
        return settingsInterface.getName();
    }

    @Override
    public void setName(String name) {
       settingsInterface.setName(name);
    }

    @Override
    public String getEditorName() {
        return settingsInterface.getEditorName();
    }

    @Override
    public String[] getEditorDescription() {
        String [] lines = settingsInterface.getEditorDescription();
        // Add into the last line the config name
        String [] newLines = new String[lines.length+1];
        for(int i = 0; i < lines.length; i++) {
            newLines[i] = lines[i];
        }
        newLines[lines.length] = "&8&o(" + settingsInterface.getName()+")";
        return newLines;
    }

    @Override
    public Material getEditorMaterial() {
        if(settingsInterface.getEditorMaterial() == null) return Material.LEVER;
        return settingsInterface.getEditorMaterial();
    }

    @Override
    public boolean isRequirePremium() {
        return settingsInterface.isRequirePremium();
    }

    @Override
    public void setRequirePremium(boolean requirePremium) {
        settingsInterface.setRequirePremium(requirePremium);
    }
}
