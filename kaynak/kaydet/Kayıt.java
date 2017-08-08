/**
 *  
 *  This file is part of SkripTR. SkripTR is free software: you can redistribute it and/or modify
 *  SkripTR is using Skript software.
 * 
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Copyright 2011-2017 Peter GÃ¼ttinger and contributors
 */

package kaydet;

import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptEventHandler;
import ch.njol.skript.events.bukkit.ScriptEvent;
import ch.njol.skript.events.util.PlayerChatEventHandler;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.util.coll.CollectionUtils;
import etkiler.Değiştir;
import etkiler.KonsolaMesajGönder;
import etkiler.MesajYayınla;
import etkiler.Olayİptal;
import etkiler.OyuncuyaMesajGönder;
import etkiler.SunucuKapat;
import etkiler.Yasakla;
import ifadeler.oplist;
import olaylar.Komut;
import olaylar.ScriptYüklendiğinde;
import olaylar.Sohbet;

@SuppressWarnings("deprecation")
public class Kayıt extends JavaPlugin {
	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
			Skript.registerAddon(this);

			// İfadeler\\
			Skript.registerExpression(oplist.class, String.class, ExpressionType.PROPERTY,
					"(oplist|opliste|oplistesi|op listesi|operatör listesi)");

			
			
			// Etkiler\\
			Skript.registerEffect(SunucuKapat.class, "sunucuyu kapat");
			Skript.registerEffect(Yasakla.class,
					"(ban|banla|yasakla) -> %string% [-> [(sebep|neden|süre|zaman):] %-string% [boyunca]] [-> [(sebep|neden|süre|zaman):] %-string% [boyunca]]",
					"%string%[[y][i][u][ü][ı]] [->] (ban|banla|yasakla) [-> [(sebep|neden|süre|zaman):] %-string% [boyunca]] [-> [(sebep|neden|süre|zaman):] %-string% [boyunca]]",
					"%string%[[y][i][u][ü][ı]] [[->] [(sebep|neden|süre|zaman):] %-string% [boyunca] [(banla|yasakla)]] [-> [(sebep|neden|süre|zaman):] %-string% [boyunca] [(banla|yasakla)]]");
			Skript.registerEffect(OyuncuyaMesajGönder.class, "mesaj gönder -> %player% [->] %string%");
			Skript.registerEffect(KonsolaMesajGönder.class, "konsola mesaj gönder [->] %string%");
			Skript.registerEffect(Olayİptal.class, "[olayı] iptal et");
			Skript.registerEffect(MesajYayınla.class, "[%worlds% dünyasındakilere] [mesaj] yayınla [->] %-strings%");
			Skript.registerEffect(Değiştir.class,
					"(artır|arttır|ekle) %~objects% -> %objects%",
					"ayarla %~objects% -> %objects%",
					"%~objects%'den tüm %objects%'leri sil",
					//"(remove|subtract) %objects% from %~objects%",
					"%~objects%[['](i|dan)] [->] %objects% [kadar] (sil|azalt|düşür)",
					"%~objects% -> (sil|temizle)",
					"%~objects% -> sıfırla"
					);

			
			
			// OLAYLAR\\
			olayKaydet(BlockDamageEvent.class, "(blo[c]k hasar aldığında|blo[c]k kırılmaya başlandığında)");
			olayKaydet(PlayerMoveEvent.class, "herhangi bir yürümede");
			olayKaydet(PlayerBucketEmptyEvent.class, "kova boşaltıldığında");
			olayKaydet(PlayerBucketFillEvent.class, "kova doldurulduğunda");
			Skript.registerEvent("", SimpleEvent.class, BlockFromToEvent.class, "(su|lav|lava) aktığında",
					"(ejderha yumurtası|blo[c]k) hareket ettiğinde");
			olayKaydet(BlockIgniteEvent.class, "(blo[c]k) yandığında");
			Skript.registerEvent("", SimpleEvent.class, BlockPhysicsEvent.class, "(kum|kırmızı kum|toprak|meşale) düştüğünde",
					"toprak yeşerdiğinde");
			olayKaydet(BlockPistonExtendEvent.class, "piston açıl(ırken|dığında)");
			olayKaydet(BlockPistonRetractEvent.class, "piston kapan(ırken|dığında)");
			olayKaydet(BlockPistonEvent.class, "piston [(açıl(ırken|dığında) veya kapan(ırken|dığında)|hareket ettiğinde)]");
			olayKaydet(BlockRedstoneEvent.class, "kızıltaş [değiştiğinde]");
			olayKaydet(BlockSpreadEvent.class, "(mantar|su|lav|lava|blo[c]k) yayıldığında");
			olayKaydet(ChunkLoadEvent.class, "(arazi|chunk) yüklendiğinde");
			olayKaydet(ChunkPopulateEvent.class, "(arazi|chunk) oluştuğunda");
			olayKaydet(ChunkUnloadEvent.class, "(arazi|chunk) geriyüklendiğinde");
			olayKaydet(CreeperPowerEvent.class, "creeper (güçlendiğinde|çarpıldığında)");
			olayKaydet(EnchantItemEvent.class, "büyü basıldığında");
			olayKaydet(EntityBreakDoorEvent.class, "zombi [tahta] kapıyı (kırarken|kırmaya çalıştığında|kırmaya çalışırken)");
			olayKaydet(EntityCombustEvent.class, "[bir] [(entity|mob|yaratık)] [(lavda|lavada|güneş ışığında|ateşte)] yanarken");
			//registerEvent(EntityPortalEvent.class, "portal");
			olayKaydet(EntityPortalEnterEvent.class, "portala (girildiğinde|girilirken)");
			olayKaydet(EntityPortalExitEvent.class, "portaldan (çıkıldığında|çıkılırken)");
			olayKaydet(EntityRegainHealthEvent.class, "can (yenilendiğinde|yenilenirken)");
			olayKaydet(EntityExplodeEvent.class, "[herhangi [bir]] [bir şey] (patlamada|patlayınca)");
			olayKaydet(EntityRegainHealthEvent.class, "[herhangi] [bir] (yaratık|yaratığın|oyuncu|oyuncunun) (can yenilediğinde|canı yenilendiğinde");
			olayKaydet(EntityTameEvent.class, "[bir] [oyuncu] (kurt|kedi|kurtu|kediyi) (evcilleştirdiğinde|evcilleştiğinde)");
			olayKaydet(FoodLevelChangeEvent.class, "(açlık|yemek) [barı] (değiştiğinde|değişirken)");
			olayKaydet(FurnaceBurnEvent.class, "fırın[da] [bir şey] (yandığında|yanıyorken)");
			olayKaydet(FurnaceSmeltEvent.class, "fırında bir şey piştiğinde"); //*
			// *: Sadece madenler için kullanılıyor olabilir, test et.
			olayKaydet(LeavesDecayEvent.class, "leaves decaying");
			olayKaydet(LightningStrikeEvent.class, "[bir] (şimşek|yıldırım) çaktığında");
			olayKaydet(PigZapEvent.class, "[bir] domuz (çarpıldığında|domuzadama dönüştüğünde)");
			olayKaydet(PlayerBedEnterEvent.class, "[yatakta] yatınca");
			olayKaydet(PlayerBedLeaveEvent.class, "yataktan kalkınca");
			olayKaydet(PlayerBucketEmptyEvent.class, "[bir] [oyuncu] kova[yı] (boşaldığında|boşalttığında|döktüğünde|döküldüğünde");
			olayKaydet(PlayerBucketFillEvent.class, "kova doldurulduğunda");
			olayKaydet(PlayerEggThrowEvent.class, "[bir] [oyuncu] yumurta[yı] (fırlattığında|fırlatıldığında)");
			olayKaydet(PlayerFishEvent.class, "[bir] [oyuncu] balık (tuttuğunda|tutulduğunda)");
			olayKaydet(PlayerItemBreakEvent.class, "[bir[isinin]] [oyuncunun] eşyası kırıldığında");
			olayKaydet(PlayerItemHeldEvent.class, "[oyuncu] eşya bölmesini değiştirdiğinde");
			olayKaydet(PlayerJoinEvent.class, "[bir] [oyuncu] [sunucuya] (girdiğinde|girildiğinde|giriş yaptığında|giriş yapıldığında)");
			olayKaydet(PlayerLoginEvent.class, "[bir] [oyuncu] [sunucuya] bağlandığında");
			olayKaydet(PlayerKickEvent.class, "[bir] [oyuncu] [sunucudan] atıldığında");
			olayKaydet(PlayerLevelChangeEvent.class, "[bir] [oyuncunun] seviye[si] değiş(tiğinde|irken)");
			olayKaydet(PlayerPortalEvent.class, "portal");
			olayKaydet(PlayerQuitEvent.class, "[bir] [oyuncu] [sunucudan] (çıktığında|çıkış yapıldığında|çıkıldığında)");
			// "sunucudan çıktığında" on left eventiyle kullanılmıyorsa ekle
			olayKaydet(PlayerRespawnEvent.class, "[bir] [oyuncu] [yeniden] (canlan(dığında|ıyorken)|doğ(duğunda|arken))");
			olayKaydet(PlayerTeleportEvent.class, "[bir] [oyuncu] ışınlan(dığında|ıyorken)");
			olayKaydet(PlayerToggleSneakEvent.class, "[bir] [oyuncu] eğil(diğinde|iyorken)");
			olayKaydet(PortalCreateEvent.class, "[bir] [oyuncu] portal (açtığında|açıldığında|oluştuğunda|oluşturduğunda)");
			olayKaydet(ProjectileHitEvent.class, "");
			//projectile hit
			olayKaydet(ProjectileLaunchEvent.class, "vurulduğunda");
			//[projectile] shoot
			olayKaydet(NotePlayEvent.class, "nota çalındığında");
			olayKaydet(SignChangeEvent.class, "[bir] tabela [(değişti(ğinde|rildiğinde)|düzenlendiğinde)]");
			olayKaydet(SpawnChangeEvent.class, "(doğma|canlanma) [(noktası|bölgesi)] değiştiğinde");
			olayKaydet(VehicleDamageEvent.class, "[bir] araç hasar aldığında");
			olayKaydet(VehicleDestroyEvent.class, "[bir] araç (parçalandığında|kırıldığında|yok edildiğinde)");
			olayKaydet(VehicleEnterEvent.class, "[bir] araca binildiğinde");
			olayKaydet(VehicleExitEvent.class, "[bir] araçtan (çıkıldığında|inildiğinde)");
			//if(Skript.classExists("org.spigotmc.event.entity.EntityMountEvent")) {
				//registerEvent(EntityMountEvent.class, "binildiğinde");
			//}
			//if(Skript.classExists("org.spigotmc.event.entity.EntityDismountEvent")) {
			//}
			olayKaydet(WorldInitEvent.class, "[bir] dünya başladığında");
			olayKaydet(WorldLoadEvent.class, "[bir] dünya yüklendiğinde");
			olayKaydet(WorldSaveEvent.class, "[bir] dünya kay(dedildiğinde|ıt edildiğinde)");
			olayKaydet(WorldUnloadEvent.class, "[bir] dünya geriyüklendiğinde");
			olayKaydet(EntityToggleGlideEvent.class, "[bir] [oyuncu] süzül(düğünde|ürken)");
			// (gliding state change|toggling gliding)
			olayKaydet(SheepRegrowWoolEvent.class, "[bir] koyunun yünü uzadığında");
			olayKaydet(InventoryOpenEvent.class, "[bir] çanta[yı] açı(nca|ldığında)");
			olayKaydet(InventoryCloseEvent.class, "[bir] çanta[yı] kapa(yınca|ndığında)");
			olayKaydet(SlimeSplitEvent.class, "[bir] balçık parçalandığında");
			olayKaydet(SpawnerSpawnEvent.class, "spawner [bir şey] canlandırdığında");
			olayKaydet(EntityResurrectEvent.class, "[bir] canlanma girişiminde");
			SkriptEventHandler.listenCancelled.add(EntityResurrectEvent.class); 
			olayKaydet(PlayerChangedWorldEvent.class, "[bir] oyuncunun dünyası değiş(tiğinde|ince)");

			
			
			//------------------//
			
			Skript.registerEvent("", Sohbet.class,
					PlayerChatEventHandler.usesAsyncEvent ? AsyncPlayerChatEvent.class : PlayerChatEvent.class,
					"sohbet edildiğinde");
			Skript.registerEvent("", ScriptYüklendiğinde.class, ScriptEvent.class,
					"[skript] (yüklendiğinde|aktif edildiğinde|aktifleştiğinde)",
					"[skript] (silindiğinde|kapatıldığında|devredışı bırakıldığında)");
			Skript.registerEvent("", Komut.class,
					CollectionUtils.array(PlayerCommandPreprocessEvent.class, ServerCommandEvent.class),
					"komut [%-string%]");

		} else {
			setEnabled(false);
		}
	}

	@SuppressWarnings("unchecked")
	public static void olayKaydet(@SuppressWarnings("rawtypes") Class class1, String string) {
		Skript.registerEvent(string, SimpleEvent.class, class1, string);
	}
}
