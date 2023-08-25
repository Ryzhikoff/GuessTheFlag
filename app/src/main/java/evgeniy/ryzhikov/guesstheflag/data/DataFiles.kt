package evgeniy.ryzhikov.guesstheflag.data

import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.domain.GameMode
import evgeniy.ryzhikov.guesstheflag.domain.Mode

class DataFiles() {
    private val countryFlag = mapOf(
        "afghanistan" to R.string.afghanistan,
        "albania" to R.string.albania,
        "algeria" to R.string.algeria,
        "american_samoa" to R.string.american_samoa,
        "andorra" to R.string.andorra,
        "angola" to R.string.angola,
        "anguilla" to R.string.anguilla,
        "antigua_and_barbuda" to R.string.antigua_and_barbuda,
        "argentina" to R.string.argentina,
        "armenia" to R.string.armenia,
        "aruba" to R.string.aruba,
        "australia" to R.string.australia,
        "austria" to R.string.austria,
        "azerbaijan" to R.string.azerbaijan,
        "bahamas" to R.string.bahamas,
        "bahrain" to R.string.bahrain,
        "bangladesh" to R.string.bangladesh,
        "barbados" to R.string.barbados,
        "belarus" to R.string.belarus,
        "belgium" to R.string.belgium,
        "belize" to R.string.belize,
        "benin" to R.string.benin,
        "bermuda" to R.string.bermuda,
        "bhutan" to R.string.bhutan,
        "bolivia" to R.string.bolivia,
        "bosnia_and_herzegovina" to R.string.bosnia_and_herzegovina,
        "botswana" to R.string.botswana,
        "brazil" to R.string.brazil,
        "british_virgin_islands" to R.string.british_virgin_islands,
        "brunei" to R.string.brunei,
        "bulgaria" to R.string.bulgaria,
        "burkina_faso" to R.string.burkina_faso,
        "burundi" to R.string.burundi,
        "cambodia" to R.string.cambodia,
        "cameroon" to R.string.cameroon,
        "canada" to R.string.canada,
        "cape_verde" to R.string.cape_verde,
        "cayman_islands" to R.string.cayman_islands,
        "central_african_republic" to R.string.central_african_republic,
        "chad" to R.string.chad,
        "chile" to R.string.chile,
        "china" to R.string.china,
        "colombia" to R.string.colombia,
        "comoros" to R.string.comoros,
        "cook_islands" to R.string.cook_islands,
        "costa_rica" to R.string.costa_rica,
        "croatia" to R.string.croatia,
        "cuba" to R.string.cuba,
        "curacao" to R.string.curacao,
        "cyprus" to R.string.cyprus,
        "czech_republic" to R.string.czech_republic,
        "democratic_republic_of_the_congo" to R.string.democratic_republic_of_the_congo,
        "denmark" to R.string.denmark,
        "djibouti" to R.string.djibouti,
        "dominica" to R.string.dominica,
        "dominican_republic" to R.string.dominican_republic,
        "ecuador" to R.string.ecuador,
        "egypt" to R.string.egypt,
        "el_salvador" to R.string.el_salvador,
        "equatorial_guinea" to R.string.equatorial_guinea,
        "eritrea" to R.string.eritrea,
        "estonia" to R.string.estonia,
        "eswatini" to R.string.eswatini,
        "ethiopia" to R.string.ethiopia,
        "faroe_islands" to R.string.faroe_islands,
        "federated_states_of_micronesia" to R.string.federated_states_of_micronesia,
        "fiji" to R.string.fiji,
        "finland" to R.string.finland,
        "france" to R.string.france,
        "french_polynesia" to R.string.french_polynesia,
        "gabon" to R.string.gabon,
        "gambia" to R.string.gambia,
        "georgia" to R.string.georgia,
        "germany" to R.string.germany,
        "ghana" to R.string.ghana,
        "gibraltar" to R.string.gibraltar,
        "greece" to R.string.greece,
        "greenland" to R.string.greenland,
        "grenada" to R.string.grenada,
        "guatemala" to R.string.guatemala,
        "guernsey" to R.string.guernsey,
        "guinea_bissau" to R.string.guinea_bissau,
        "guinea" to R.string.guinea,
        "guyana" to R.string.guyana,
        "haiti" to R.string.haiti,
        "honduras" to R.string.honduras,
        "hong_kong" to R.string.hong_kong,
        "hungary" to R.string.hungary,
        "iceland" to R.string.iceland,
        "india" to R.string.india,
        "indonesia" to R.string.indonesia,
        "iran" to R.string.iran,
        "iraq" to R.string.iraq,
        "ireland" to R.string.ireland,
        "isle_of_man" to R.string.isle_of_man,
        "israel" to R.string.israel,
        "italy" to R.string.italy,
        "ivory_coast" to R.string.ivory_coast,
        "jamaica" to R.string.jamaica,
        "japan" to R.string.japan,
        "jersey" to R.string.jersey,
        "jordan" to R.string.jordan,
        "kazakhstan" to R.string.kazakhstan,
        "kenya" to R.string.kenya,
        "kiribati" to R.string.kiribati,
        "kosovo" to R.string.kosovo,
        "kuwait" to R.string.kuwait,
        "kyrgyzstan" to R.string.kyrgyzstan,
        "laos" to R.string.laos,
        "latvia" to R.string.latvia,
        "lebanon" to R.string.lebanon,
        "lesotho" to R.string.lesotho,
        "liberia" to R.string.liberia,
        "libya" to R.string.libya,
        "liechtenstein" to R.string.liechtenstein,
        "lithuania" to R.string.lithuania,
        "luxembourg" to R.string.luxembourg,
        "macau" to R.string.macau,
        "madagascar" to R.string.madagascar,
        "malawi" to R.string.malawi,
        "malaysia" to R.string.malaysia,
        "maldives" to R.string.maldives,
        "mali" to R.string.mali,
        "malta" to R.string.malta,
        "marshall_Islands" to R.string.marshall_Islands,
        "mauritania" to R.string.mauritania,
        "mauritius" to R.string.mauritius,
        "mexico" to R.string.mexico,
        "moldova" to R.string.moldova,
        "monaco" to R.string.monaco,
        "mongolia" to R.string.mongolia,
        "montenegro" to R.string.montenegro,
        "montserrat" to R.string.montserrat,
        "morocco" to R.string.morocco,
        "mozambique" to R.string.mozambique,
        "myanmar" to R.string.myanmar,
        "namibia" to R.string.namibia,
        "nauru" to R.string.nauru,
        "nepal" to R.string.nepal,
        "netherlands" to R.string.netherlands,
        "new_caledonia" to R.string.new_caledonia,
        "new_zealand" to R.string.new_zealand,
        "nicaragua" to R.string.nicaragua,
        "niger" to R.string.niger,
        "nigeria" to R.string.nigeria,
        "northern_mariana_islands" to R.string.northern_mariana_islands,
        "north_korea" to R.string.north_korea,
        "north_macedonia" to R.string.north_macedonia,
        "norway" to R.string.norway,
        "oman" to R.string.oman,
        "pakistan" to R.string.pakistan,
        "palau" to R.string.palau,
        "palestine" to R.string.palestine,
        "panama" to R.string.panama,
        "papua_new_guinea" to R.string.papua_new_guinea,
        "paraguay" to R.string.paraguay,
        "peru" to R.string.peru,
        "philippines" to R.string.philippines,
        "poland" to R.string.poland,
        "portugal" to R.string.portugal,
        "puerto_rico" to R.string.puerto_rico,
        "qatar" to R.string.qatar,
        "republic_of_the_congo" to R.string.republic_of_the_congo,
        "romania" to R.string.romania,
        "russia" to R.string.russia,
        "rwanda" to R.string.rwanda,
        "saint_barthelemy" to R.string.saint_barthelemy,
        "saint_helena_ascension_and_tristan_da_cunha" to R.string.saint_helena_ascension_and_tristan_da_cunha,
        "saint_kitts_and_nevis" to R.string.saint_kitts_and_nevis,
        "saint_lucia" to R.string.saint_lucia,
        "saint_martin" to R.string.saint_martin,
        "saint_pierre_and_miquelon" to R.string.saint_pierre_and_miquelon,
        "saint_vincent_and_the_grenadines" to R.string.saint_vincent_and_the_grenadines,
        "samoa" to R.string.samoa,
        "san_marino" to R.string.san_marino,
        "sao_tome_and_principe" to R.string.sao_tome_and_principe,
        "saudi_arabia" to R.string.saudi_arabia,
        "senegal" to R.string.senegal,
        "serbia" to R.string.serbia,
        "seychelles" to R.string.seychelles,
        "sierra_leone" to R.string.sierra_leone,
        "singapore" to R.string.singapore,
        "sint_maarten" to R.string.sint_maarten,
        "slovakia" to R.string.slovakia,
        "slovenia" to R.string.slovenia,
        "solomon_islands" to R.string.solomon_islands,
        "somalia" to R.string.somalia,
        "south_africa" to R.string.south_africa,
        "south_korea" to R.string.south_korea,
        "south_sudan" to R.string.south_sudan,
        "spain" to R.string.spain,
        "sri_lanka" to R.string.sri_lanka,
        "sudan" to R.string.sudan,
        "suriname" to R.string.suriname,
        "sweden" to R.string.sweden,
        "switzerland" to R.string.switzerland,
        "syria" to R.string.syria,
        "taiwan" to R.string.taiwan,
        "tajikistan" to R.string.tajikistan,
        "tanzania" to R.string.tanzania,
        "thailand" to R.string.thailand,
        "timor_leste" to R.string.timor_leste,
        "togo" to R.string.togo,
        "tonga" to R.string.tonga,
        "trinidad_and_tobago" to R.string.trinidad_and_tobago,
        "tunisia" to R.string.tunisia,
        "turkey" to R.string.turkey,
        "turkmenistan" to R.string.turkmenistan,
        "turks_and_caicos_islands" to R.string.turks_and_caicos_islands,
        "tuvalu" to R.string.tuvalu,
        "guam" to R.string.guam,
        "uganda" to R.string.uganda,
        "ukraine" to R.string.ukraine,
        "united_arab_emirates" to R.string.united_arab_emirates,
        "united_kingdom" to R.string.united_kingdom,
        "united_states" to R.string.united_states,
        "united_states_virgin_islands" to R.string.united_states_virgin_islands,
        "uruguay" to R.string.uruguay,
        "uzbekistan" to R.string.uzbekistan,
        "vanuatu" to R.string.vanuatu,
        "venezuela" to R.string.venezuela,
        "vietnam" to R.string.vietnam,
        "wallis_and_futuna" to R.string.wallis_and_futuna,
        "yemen" to R.string.yemen,
        "zambia" to R.string.zambia,
        "zimbabwe" to R.string.zimbabwe
    )
    private val regionFlag = mapOf(
        "usa_alabama" to R.string.usa_alabama,
        "usa_alaska" to R.string.usa_alaska,
        "usa_arizona" to R.string.usa_arizona,
        "usa_arkansas" to R.string.usa_arkansas,
        "usa_california" to R.string.usa_california,
        "usa_colorado" to R.string.usa_colorado,
        "usa_connecticut" to R.string.usa_connecticut,
        "usa_delaware" to R.string.usa_delaware,
        "usa_district_of_columbia" to R.string.usa_district_of_columbia,
        "usa_florida" to R.string.usa_florida,
        "usa_georgia" to R.string.usa_georgia,
        "usa_hawaii" to R.string.usa_hawaii,
        "usa_idaho" to R.string.usa_idaho,
        "usa_illinois" to R.string.usa_illinois,
        "usa_indiana" to R.string.usa_indiana,
        "usa_iowa" to R.string.usa_iowa,
        "usa_kansas" to R.string.usa_kansas,
        "usa_kentucky" to R.string.usa_kentucky,
        "usa_louisiana" to R.string.usa_louisiana,
        "usa_maine" to R.string.usa_maine,
        "usa_maryland" to R.string.usa_maryland,
        "usa_massachusetts" to R.string.usa_massachusetts,
        "usa_michigan" to R.string.usa_michigan,
        "usa_minnesota" to R.string.usa_minnesota,
        "usa_mississippi" to R.string.usa_mississippi,
        "usa_missouri" to R.string.usa_missouri,
        "usa_montana" to R.string.usa_montana,
        "usa_nebraska" to R.string.usa_nebraska,
        "usa_nevada" to R.string.usa_nevada,
        "usa_new_hampshire" to R.string.usa_new_hampshire,
        "usa_new_jersey" to R.string.usa_new_jersey,
        "usa_new_mexico" to R.string.usa_new_mexico,
        "usa_new_york" to R.string.usa_new_york,
        "usa_north_carolina" to R.string.usa_north_carolina,
        "usa_north_dakota" to R.string.usa_north_dakota,
        "usa_ohio" to R.string.usa_ohio,
        "usa_oklahoma" to R.string.usa_oklahoma,
        "usa_oregon" to R.string.usa_oregon,
        "usa_pennsylvania" to R.string.usa_pennsylvania,
        "usa_rhode_island" to R.string.usa_rhode_island,
        "usa_south_carolina" to R.string.usa_south_carolina,
        "usa_south_dakota" to R.string.usa_south_dakota,
        "usa_tennessee" to R.string.usa_tennessee,
        "usa_texas" to R.string.usa_texas,
        "usa_utah" to R.string.usa_utah,
        "usa_vermont" to R.string.usa_vermont,
        "usa_virginia" to R.string.usa_virginia,
        "usa_washington" to R.string.usa_washington,
        "usa_west_virginia" to R.string.usa_west_virginia,
        "usa_wisconsin" to R.string.usa_wisconsin,
        "usa_wyoming" to R.string.usa_wyoming,

        "russia_adygea" to R.string.russia_adygea,
        "russia_altai_krai" to R.string.russia_altai_krai,
        "russia_altai_republic" to R.string.russia_altai_republic,
        "russia_amur_oblast" to R.string.russia_amur_oblast,
        "russia_arkhangelsk_oblast" to R.string.russia_arkhangelsk_oblast,
        "russia_astrakhan_oblast" to R.string.russia_astrakhan_oblast,
        "russia_bashkortostan" to R.string.russia_bashkortostan,
        "russia_belgorod_oblast" to R.string.russia_belgorod_oblast,
        "russia_bryansk_oblast" to R.string.russia_bryansk_oblast,
        "russia_buryatia" to R.string.russia_buryatia,
        "russia_chechen_republic" to R.string.russia_chechen_republic,
        "russia_chelyabinsk_oblast" to R.string.russia_chelyabinsk_oblast,
        "russia_chukotka" to R.string.russia_chukotka,
        "russia_chuvashia" to R.string.russia_chuvashia,
        "russia_dagestan" to R.string.russia_dagestan,
        "russia_ingushetia" to R.string.russia_ingushetia,
        "russia_irkutsk_oblast" to R.string.russia_irkutsk_oblast,
        "russia_ivanovo_oblast" to R.string.russia_ivanovo_oblast,
        "russia_jewish_autonomous_oblast" to R.string.russia_jewish_autonomous_oblast,
        "russia_kabardino_balkaria" to R.string.russia_kabardino_balkaria,
        "russia_kaliningrad_oblast" to R.string.russia_kaliningrad_oblast,
        "russia_kalmykia" to R.string.russia_kalmykia,
        "russia_kaluga_oblast" to R.string.russia_kaluga_oblast,
        "russia_kamchatka_krai" to R.string.russia_kamchatka_krai,
        "russia_karachay_cherkessia" to R.string.russia_karachay_cherkessia,
        "russia_karelia" to R.string.russia_karelia,
        "russia_kemerovo_oblast" to R.string.russia_kemerovo_oblast,
        "russia_khabarovsk_krai" to R.string.russia_khabarovsk_krai,
        "russia_khakassia" to R.string.russia_khakassia,
        "russia_kirov_oblast" to R.string.russia_kirov_oblast,
        "russia_komi" to R.string.russia_komi,
        "russia_kostroma_oblast" to R.string.russia_kostroma_oblast,
        "russia_krasnodar_krai" to R.string.russia_krasnodar_krai,
        "russia_krasnoyarsk_krai" to R.string.russia_krasnoyarsk_krai,
        "russia_kurgan_oblast" to R.string.russia_kurgan_oblast,
        "russia_kursk_oblast" to R.string.russia_kursk_oblast,
        "russia_leningrad_oblast" to R.string.russia_leningrad_oblast,
        "russia_lipetsk_oblast" to R.string.russia_lipetsk_oblast,
        "russia_magadan_oblast" to R.string.russia_magadan_oblast,
        "russia_mari_el" to R.string.russia_mari_el,
        "russia_mordovia" to R.string.russia_mordovia,
        "russia_moscow" to R.string.russia_moscow,
        "russia_moscow_oblast" to R.string.russia_moscow_oblast,
        "russia_murmansk_oblast" to R.string.russia_murmansk_oblast,
        "russia_nenets_autonomous_district" to R.string.russia_nenets_autonomous_district,
        "russia_nizhny_novgorod_region" to R.string.russia_nizhny_novgorod_region,
        "russia_north_ossetia" to R.string.russia_north_ossetia,
        "russia_novgorod_oblast" to R.string.russia_novgorod_oblast,
        "russia_novosibirsk__oblast" to R.string.russia_novosibirsk__oblast,
        "russia_omsk_oblast" to R.string.russia_omsk_oblast,
        "russia_orenburg_oblast" to R.string.russia_orenburg_oblast,
        "russia_penza_oblast" to R.string.russia_penza_oblast,
        "russia_perm_krai" to R.string.russia_perm_krai,
        "russia_primorsky_krai" to R.string.russia_primorsky_krai,
        "russia_pskov_oblast" to R.string.russia_pskov_oblast,
        "russia_rostov_oblast" to R.string.russia_rostov_oblast,
        "russia_ryazan_oblast" to R.string.russia_ryazan_oblast,
        "russia_saint_petersburg" to R.string.russia_saint_petersburg,
        "russia_sakha" to R.string.russia_sakha,
        "russia_sakhalin_oblast" to R.string.russia_sakhalin_oblast,
        "russia_samara_oblast" to R.string.russia_samara_oblast,
        "russia_saratov_oblast" to R.string.russia_saratov_oblast,
        "russia_smolensk_oblast" to R.string.russia_smolensk_oblast,
        "russia_stavropol_krai" to R.string.russia_stavropol_krai,
        "russia_sverdlovsk_oblast" to R.string.russia_sverdlovsk_oblast,
        "russia_tambov_oblast" to R.string.russia_tambov_oblast,
        "russia_tatarstan" to R.string.russia_tatarstan,
        "russia_tomsk_oblast" to R.string.russia_tomsk_oblast,
        "russia_tula_oblast" to R.string.russia_tula_oblast,
        "russia_tuva" to R.string.russia_tuva,
        "russia_tver_oblast" to R.string.russia_tver_oblast,
        "russia_tyumen_oblast" to R.string.russia_tyumen_oblast,
        "russia_udmurtia" to R.string.russia_udmurtia,
        "russia_ulyanovsk_oblast" to R.string.russia_ulyanovsk_oblast,
        "russia_vladimir_oblast" to R.string.russia_vladimir_oblast,
        "russia_volgograd_oblast" to R.string.russia_volgograd_oblast,
        "russia_vologda_oblast" to R.string.russia_vologda_oblast,
        "russia_voronezh_oblast" to R.string.russia_voronezh_oblast,
        "russia_yamal_nenets_autonomous_district" to R.string.russia_yamal_nenets_autonomous_district,
        "russia_yaroslavl_oblast" to R.string.russia_yaroslavl_oblast,
        "russia_yugra" to R.string.russia_yugra,

        "russia_zabaykalsky_krai" to R.string.russia_zabaykalsky_krai,
        "belarus_brest_oblast" to R.string.belarus_brest_oblast,
        "belarus_homyel_oblast" to R.string.belarus_homyel_oblast,
        "belarus_hrodna_oblasts" to R.string.belarus_hrodna_oblasts,
        "belarus_mahilyow_oblast" to R.string.belarus_mahilyow_oblast,
        "belarus_minsk" to R.string.belarus_minsk,
        "belarus_minsk_oblast" to R.string.belarus_minsk_oblast,
        "belarus_vitsebsk_oblast" to R.string.belarus_vitsebsk_oblast,

        "france_auvergne_rhone_alpes" to R.string.france_auvergne_rhone_alpes,
        "france_bourgogne_franche_comte" to R.string.france_bourgogne_franche_comte,
        "france_brittany" to R.string.france_brittany,
        "france_centre_val_de_loire" to R.string.france_centre_val_de_loire,
        "france_corsica" to R.string.france_corsica,
        "france_hauts_de_france" to R.string.france_hauts_de_france,
        "france_normandie" to R.string.france_normandie,
        "france_nouvelle_aquitaine" to R.string.france_nouvelle_aquitaine,
        "france_occitanie" to R.string.france_occitanie,
        "france_pays_de_la_loire" to R.string.france_pays_de_la_loire,
        "france_provence_alpes_cote_d_azur" to R.string.france_provence_alpes_cote_d_azur,

        "germany_baden_wurttemberg" to R.string.germany_baden_wurttemberg,
        "germany_bavaria" to R.string.germany_bavaria,
        "germany_berlin" to R.string.germany_berlin,
        "germany_brandenburg" to R.string.germany_brandenburg,
        "germany_bremen" to R.string.germany_bremen,
        "germany_hamburg" to R.string.germany_hamburg,
        "germany_hesse" to R.string.germany_hesse,
        "germany_lower_saxony" to R.string.germany_lower_saxony,
        "germany_mecklenburg_western_pomerania" to R.string.germany_mecklenburg_western_pomerania,
        "germany_north_rhine_westphalia" to R.string.germany_north_rhine_westphalia,
        "germany_rhineland_palatinate" to R.string.germany_rhineland_palatinate,
        "germany_saxony" to R.string.germany_saxony,
        "germany_saxony_anhalt" to R.string.germany_saxony_anhalt,
        "germany_schleswig_holstein" to R.string.germany_schleswig_holstein,
        "germany_thuringia" to R.string.germany_thuringia,

        "italy_abruzzo" to R.string.italy_abruzzo,
        "italy_apulia" to R.string.italy_apulia,
        "italy_basilicata" to R.string.italy_basilicata,
        "italy_calabria" to R.string.italy_calabria,
        "italy_campania" to R.string.italy_campania,
        "italy_friuli_venezia_giulia" to R.string.italy_friuli_venezia_giulia,
        "italy_lazio" to R.string.italy_lazio,
        "italy_liguria" to R.string.italy_liguria,
        "italy_lombardy" to R.string.italy_lombardy,
        "italy_marche" to R.string.italy_marche,
        "italy_molise" to R.string.italy_molise,
        "italy_piedmont" to R.string.italy_piedmont,
        "italy_sardinia" to R.string.italy_sardinia,
        "italy_sicilian" to R.string.italy_sicilian,
        "italy_trentino_south_tyrol" to R.string.italy_trentino_south_tyrol,
        "italy_tuscany" to R.string.italy_tuscany,
        "italy_umbria" to R.string.italy_umbria,
        "italy_valle_d_aosta" to R.string.italy_valle_d_aosta,
        "italy_veneto" to R.string.italy_veneto,

        "spain_andalucia" to R.string.spain_andalucia,
        "spain_aragon" to R.string.spain_aragon,
        "spain_asturias" to R.string.spain_asturias,
        "spain_balearic_Islands" to R.string.spain_balearic_Islands,
        "spain_bandera_de_navarra" to R.string.spain_bandera_de_navarra,
        "spain_basque_country" to R.string.spain_basque_country,
        "spain_canary_Islands" to R.string.spain_canary_Islands,
        "spain_cantabria" to R.string.spain_cantabria,
        "spain_castile_and_leon" to R.string.spain_castile_and_leon,
        "spain_castilla_la_mancha" to R.string.spain_castilla_la_mancha,
        "spain_catalonia" to R.string.spain_catalonia,
        "spain_ceuta" to R.string.spain_ceuta,
        "spain_community_of_madrid" to R.string.spain_community_of_madrid,
        "spain_extremadura" to R.string.spain_extremadura,
        "spain_galicia" to R.string.spain_galicia,
        "spain_land_of_valencia" to R.string.spain_land_of_valencia,
        "spain_la_rioja" to R.string.spain_la_rioja,
        "spain_melilla" to R.string.spain_melilla,
        "spain_region_of_murcia" to R.string.spain_region_of_murcia,

        "poland_wojewodztwo_dolnoslaskie" to R.string.poland_wojewodztwo_dolnoslaskie,
        "poland_wojewodztwo_kujawsko_pomorskie" to R.string.poland_wojewodztwo_kujawsko_pomorskie,
        "poland_wojewodztwo_lodzkie" to R.string.poland_wojewodztwo_lodzkie,
        "poland_wojewodztwo_lubelskie" to R.string.poland_wojewodztwo_lubelskie,
        "poland_wojewodztwo_lubuskie" to R.string.poland_wojewodztwo_lubuskie,
        "poland_wojewodztwo_malopolskie" to R.string.poland_wojewodztwo_malopolskie,
        "poland_wojewodztwo_mazowieckie" to R.string.poland_wojewodztwo_mazowieckie,
        "poland_wojewodztwo_opolskie" to R.string.poland_wojewodztwo_opolskie,
        "poland_wojewodztwo_podkarpackie" to R.string.poland_wojewodztwo_podkarpackie,
        "poland_wojewodztwo_podlaskie" to R.string.poland_wojewodztwo_podlaskie,
        "poland_wojewodztwo_pomorskie" to R.string.poland_wojewodztwo_pomorskie,
        "poland_wojewodztwo_slaskie" to R.string.poland_wojewodztwo_slaskie,
        "poland_wojewodztwo_swietokrzyskie" to R.string.poland_wojewodztwo_swietokrzyskie,
        "poland_wojewodztwo_warminsko_mazurskie" to R.string.poland_wojewodztwo_warminsko_mazurskie,
        "poland_wojewodztwo_zachodniopomorskie" to R.string.poland_wojewodztwo_zachodniopomorskie,

        "lithuania_aukstaitija" to R.string.lithuania_aukstaitija,
        "lithuania_dzukija" to R.string.lithuania_dzukija,
        "lithuania_lithuania_minor" to R.string.lithuania_lithuania_minor,
        "lithuania_suvalkija" to R.string.lithuania_suvalkija,
        "lithuania_wojewodztwo_wielkopolskie" to R.string.lithuania_wojewodztwo_wielkopolskie,
        "lithuania_zemaitija" to R.string.lithuania_zemaitija,

        "ukraine_cherkasy_oblast" to R.string.ukraine_cherkasy_oblast,
        "ukraine_chernivtsi_oblast" to R.string.ukraine_chernivtsi_oblast,
        "ukraine_dnipropetrovsk_oblast" to R.string.ukraine_dnipropetrovsk_oblast,
        "ukraine_ivano_frankivsk_oblast" to R.string.ukraine_ivano_frankivsk_oblast,
        "ukraine_kharkiv_oblast" to R.string.ukraine_kharkiv_oblast,
        "ukraine_khmelnytskyi_oblast" to R.string.ukraine_khmelnytskyi_oblast,
        "ukraine_kirovohrad_oblast" to R.string.ukraine_kirovohrad_oblast,
        "ukraine_kyiv_kurovskyi" to R.string.ukraine_kyiv,
        "ukraine_kyiv_oblast" to R.string.ukraine_kyiv_oblast,
        "ukraine_lviv_oblast" to R.string.ukraine_lviv_oblast,
        "ukraine_mykolaiv_oblast" to R.string.ukraine_mykolaiv_oblast,
        "ukraine_odesa_oblast" to R.string.ukraine_odesa_oblast,
        "ukraine_poltava_oblast" to R.string.ukraine_poltava_oblast,
        "ukraine_rivne_oblast" to R.string.ukraine_rivne_oblast,
        "ukraine_sumy_oblast" to R.string.ukraine_sumy_oblast,
        "ukraine_ternopil_oblast" to R.string.ukraine_ternopil_oblast,
        "ukraine_transcarpathian_oblast" to R.string.ukraine_transcarpathian_oblast,
        "ukraine_vinnytskaya_oblast" to R.string.ukraine_vinnytskaya_oblast,
        "ukraine_volyn_oblast" to R.string.ukraine_volyn_oblast,
        "ukraine_zhytomyr_oblast" to R.string.ukraine_zhytomyr_oblast,

        )
    private val countryMap = mapOf<String, Int>()
    private val regionMap = mapOf<String, Int>()

    fun getMapOfNames() : Map<String, Int> {
        return when(GameMode.mode) {
            Mode.COUNTRY_FLAG -> countryFlag
            Mode.REGION_FLAG -> regionFlag
            Mode.COUNTRY_MAP -> countryMap
            Mode.REGION_MAP -> regionMap
            else -> countryFlag
        }
    }
}