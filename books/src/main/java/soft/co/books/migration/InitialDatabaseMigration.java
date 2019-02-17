package soft.co.books.migration;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import soft.co.books.domain.collection.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "001")
public class InitialDatabaseMigration {

    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthority")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(new Authority("user-management", "Create or update user data"));
        authorityList.add(new Authority("user-list", "Users list"));
        authorityList.add(new Authority("book-list", "Books list"));
        authorityList.add(new Authority("book-trace", "Books trace"));
        authorityList.add(new Authority("book-management", "Create or update book data"));
        authorityList.add(new Authority("author-management", "Create or update author data"));
        authorityList.add(new Authority("author-list", "Authors list"));
        authorityList.add(new Authority("editor-management", "Create or update editor data"));
        authorityList.add(new Authority("editor-list", "Editors list"));
        authorityList.add(new Authority("descriptor-management", "Create or update descriptor data"));
        authorityList.add(new Authority("descriptor-list", "Descriptors list"));
        authorityList.add(new Authority("magazine-management", "Create or update magazine data"));
        authorityList.add(new Authority("magazine-list", "Magazines list"));
        authorityList.add(new Authority("topic-management", "Create or update topic data"));
        authorityList.add(new Authority("topic-list", "Topics list"));
        authorityList.add(new Authority("sale-management", "Update sale data"));
        authorityList.add(new Authority("sale-list", "Sales list"));
//        authorityList.add(new Authority("trace-list", "Trace list"));
        authorityList.add(new Authority("search-sale", "Search sale"));
        authorityList.add(new Authority("info-management", "Create or update contact data"));

        mongoTemplate.insertAll(authorityList);
    }

    @ChangeSet(order = "02", author = "initiator", id = "02-addCountries")
    public void addCountries(MongoTemplate mongoTemplate) {
        String cal = "Afganistán\",\"Afghanistan\",\"Afghanistan\",\"AF\",\"AFG\",\"93@Albania\",\"Albania\",\"Albanie\",\"AL\",\"ALB\",\"355@Alemania\",\"Germany\",\"Allemagne\",\"DE\",\"DEU\",\"49@Argelia\",\"Algeria\",\"Algérie\",\"DZ\",\"DZA\",\"213@Andorra\",\"Andorra\",\"Andorra\",\"AD\",\"AND\",\"376@Angola\",\"Angola\",\"Angola\",\"AO\",\"AGO\",\"244@Anguila\",\"Anguilla\",\"Anguilla\",\"AI\",\"AIA\",\"1 264@Antártida\",\"Antarctica\",\"L'Antarctique\",\"AQ\",\"ATA\",\"672@Antigua y Barbuda\",\"Antigua and Barbuda\",\"Antigua et Barbuda\",\"AG\",\"ATG\",\"1 268@Antillas Neerlandesas\",\"Netherlands Antilles\",\"Antilles Néerlandaises\",\"AN\",\"ANT\",\"599@Arabia Saudita\",\"Saudi Arabia\",\"Arabie Saoudite\",\"SA\",\"SAU\",\"966@Argentina\",\"Argentina\",\"Argentine\",\"AR\",\"ARG\",\"54@Armenia\",\"Armenia\",\"L'Arménie\",\"AM\",\"ARM\",\"374@Aruba\",\"Aruba\",\"Aruba\",\"AW\",\"ABW\",\"297@Australia\",\"Australia\",\"Australie\",\"AU\",\"AUS\",\"61@Austria\",\"Austria\",\"Autriche\",\"AT\",\"AUT\",\"43@Azerbayán\",\"Azerbaijan\",\"L'Azerbaïdjan\",\"AZ\",\"AZE\",\"994@Bélgica\",\"Belgium\",\"Belgique\",\"BE\",\"BEL\",\"32@Bahamas\",\"Bahamas\",\"Bahamas\",\"BS\",\"BHS\",\"1 242@Baréin\",\"Bahrain\",\"Bahreïn\",\"BH\",\"BHR\",\"973@Bangladesh\",\"Bangladesh\",\"Bangladesh\",\"BD\",\"BGD\",\"880@Barbados\",\"Barbados\",\"Barbade\",\"BB\",\"BRB\",\"1 246@Belice\",\"Belize\",\"Belize\",\"BZ\",\"BLZ\",\"501@Benín\",\"Benin\",\"Bénin\",\"BJ\",\"BEN\",\"229@Bután\",\"Bhutan\",\"Le Bhoutan\",\"BT\",\"BTN\",\"975@Bielorrusia\",\"Belarus\",\"Biélorussie\",\"BY\",\"BLR\",\"375@Birmania\",\"Myanmar\",\"Myanmar\",\"MM\",\"MMR\",\"95@Bolivia\",\"Bolivia\",\"Bolivie\",\"BO\",\"BOL\",\"591@Bosnia y Herzegovina\",\"Bosnia and Herzegovina\",\"Bosnie-Herzégovine\",\"BA\",\"BIH\",\"387@Botsuana\",\"Botswana\",\"Botswana\",\"BW\",\"BWA\",\"267@Brasil\",\"Brazil\",\"Brésil\",\"BR\",\"BRA\",\"55@Brunéi\",\"Brunei\",\"Brunei\",\"BN\",\"BRN\",\"673@Bulgaria\",\"Bulgaria\",\"Bulgarie\",\"BG\",\"BGR\",\"359@Burkina Faso\",\"Burkina Faso\",\"Burkina Faso\",\"BF\",\"BFA\",\"226@Burundi\",\"Burundi\",\"Burundi\",\"BI\",\"BDI\",\"257@Cabo Verde\",\"Cape Verde\",\"Cap-Vert\",\"CV\",\"CPV\",\"238@Camboya\",\"Cambodia\",\"Cambodge\",\"KH\",\"KHM\",\"855@Camerún\",\"Cameroon\",\"Cameroun\",\"CM\",\"CMR\",\"237@Canadá\",\"Canada\",\"Canada\",\"CA\",\"CAN\",\"1@Chad\",\"Chad\",\"Tchad\",\"TD\",\"TCD\",\"235@Chile\",\"Chile\",\"Chili\",\"CL\",\"CHL\",\"56@China\",\"China\",\"Chine\",\"CN\",\"CHN\",\"86@Chipre\",\"Cyprus\",\"Chypre\",\"CY\",\"CYP\",\"357@Ciudad del Vaticano\",\"Vatican City State\",\"Cité du Vatican\",\"VA\",\"VAT\",\"39@Colombia\",\"Colombia\",\"Colombie\",\"CO\",\"COL\",\"57@Comoras\",\"Comoros\",\"Comores\",\"KM\",\"COM\",\"269@Congo\",\"Congo\",\"Congo\",\"CG\",\"COG\",\"242@Congo\",\"Congo\",\"Congo\",\"CD\",\"COD\",\"243@Corea del Norte\",\"North Korea\",\"Corée du Nord\",\"KP\",\"PRK\",\"850@Corea del Sur\",\"South Korea\",\"Corée du Sud\",\"KR\",\"KOR\",\"82@Costa de Marfil\",\"Ivory Coast\",\"Côte-d'Ivoire\",\"CI\",\"CIV\",\"225@Costa Rica\",\"Costa Rica\",\"Costa Rica\",\"CR\",\"CRI\",\"506@Croacia\",\"Croatia\",\"Croatie\",\"HR\",\"HRV\",\"385@Cuba\",\"Cuba\",\"Cuba\",\"CU\",\"CUB\",\"53@Dinamarca\",\"Denmark\",\"Danemark\",\"DK\",\"DNK\",\"45@Dominica\",\"Dominica\",\"Dominique\",\"DM\",\"DMA\",\"1 767@Ecuador\",\"Ecuador\",\"Equateur\",\"EC\",\"ECU\",\"593@Egipto\",\"Egypt\",\"Egypte\",\"EG\",\"EGY\",\"20@El Salvador\",\"El Salvador\",\"El Salvador\",\"SV\",\"SLV\",\"503@Emiratos Árabes Unidos\",\"United Arab Emirates\",\"Emirats Arabes Unis\",\"AE\",\"ARE\",\"971@Eritrea\",\"Eritrea\",\"Erythrée\",\"ER\",\"ERI\",\"291@Eslovaquia\",\"Slovakia\",\"Slovaquie\",\"SK\",\"SVK\",\"421@Eslovenia\",\"Slovenia\",\"Slovénie\",\"SI\",\"SVN\",\"386@España\",\"Spain\",\"Espagne\",\"ES\",\"ESP\",\"34@Estados Unidos de América\",\"United States of America\",\"États-Unis d'Amérique\",\"US\",\"USA\",\"1@Estonia\",\"Estonia\",\"L'Estonie\",\"EE\",\"EST\",\"372@Etiopía\",\"Ethiopia\",\"Ethiopie\",\"ET\",\"ETH\",\"251@Filipinas\",\"Philippines\",\"Philippines\",\"PH\",\"PHL\",\"63@Finlandia\",\"Finland\",\"Finlande\",\"FI\",\"FIN\",\"358@Fiyi\",\"Fiji\",\"Fidji\",\"FJ\",\"FJI\",\"679@Francia\",\"France\",\"France\",\"FR\",\"FRA\",\"33@Gabón\",\"Gabon\",\"Gabon\",\"GA\",\"GAB\",\"241@Gambia\",\"Gambia\",\"Gambie\",\"GM\",\"GMB\",\"220@Georgia\",\"Georgia\",\"Géorgie\",\"GE\",\"GEO\",\"995@Ghana\",\"Ghana\",\"Ghana\",\"GH\",\"GHA\",\"233@Gibraltar\",\"Gibraltar\",\"Gibraltar\",\"GI\",\"GIB\",\"350@Granada\",\"Grenada\",\"Grenade\",\"GD\",\"GRD\",\"1 473@Grecia\",\"Greece\",\"Grèce\",\"GR\",\"GRC\",\"30@Groenlandia\",\"Greenland\",\"Groenland\",\"GL\",\"GRL\",\"299@Guadalupe\",\"Guadeloupe\",\"Guadeloupe\",\"GP\",\"GLP\",@\"Guam\",\"Guam\",\"Guam\",\"GU\",\"GUM\",\"1 671@Guatemala\",\"Guatemala\",\"Guatemala\",\"GT\",\"GTM\",\"502@Guayana Francesa\",\"French Guiana\",\"Guyane française\",\"GF\",\"GUF\",@\"Guernsey\",\"Guernsey\",\"Guernesey\",\"GG\",\"GGY\",@\"Guinea\",\"Guinea\",\"Guinée\",\"GN\",\"GIN\",\"224@Guinea Ecuatorial\",\"Equatorial Guinea\",\"Guinée Equatoriale\",\"GQ\",\"GNQ\",\"240@Guinea-Bissau\",\"Guinea-Bissau\",\"Guinée-Bissau\",\"GW\",\"GNB\",\"245@Guyana\",\"Guyana\",\"Guyane\",\"GY\",\"GUY\",\"592@Haití\",\"Haiti\",\"Haïti\",\"HT\",\"HTI\",\"509@Honduras\",\"Honduras\",\"Honduras\",\"HN\",\"HND\",\"504@Hong kong\",\"Hong Kong\",\"Hong Kong\",\"HK\",\"HKG\",\"852@Hungría\",\"Hungary\",\"Hongrie\",\"HU\",\"HUN\",\"36@India\",\"India\",\"Inde\",\"IN\",\"IND\",\"91@Indonesia\",\"Indonesia\",\"Indonésie\",\"ID\",\"IDN\",\"62@Irán\",\"Iran\",\"Iran\",\"IR\",\"IRN\",\"98@Irak\",\"Iraq\",\"Irak\",\"IQ\",\"IRQ\",\"964@Irlanda\",\"Ireland\",\"Irlande\",\"IE\",\"IRL\",\"353@Isla Bouvet\",\"Bouvet Island\",\"Bouvet Island\",\"BV\",\"BVT\",@\"Isla de Man\",\"Isle of Man\",\"Ile de Man\",\"IM\",\"IMN\",\"44@Isla de Navidad\",\"Christmas Island\",\"Christmas Island\",\"CX\",\"CXR\",\"61@Isla Norfolk\",\"Norfolk Island\",\"Île de Norfolk\",\"NF\",\"NFK\",@\"Islandia\",\"Iceland\",\"Islande\",\"IS\",\"ISL\",\"354@Islas Bermudas\",\"Bermuda Islands\",\"Bermudes\",\"BM\",\"BMU\",\"1 441@Islas Caimán\",\"Cayman Islands\",\"Iles Caïmans\",\"KY\",\"CYM\",\"1 345@Islas Cocos (Keeling)\",\"Cocos (Keeling) Islands\",\"Cocos (Keeling\",\"CC\",\"CCK\",\"61@Islas Cook\",\"Cook Islands\",\"Iles Cook\",\"CK\",\"COK\",\"682@Islas de Åland\",\"Åland Islands\",\"Îles Åland\",\"AX\",\"ALA\",@\"Islas Feroe\",\"Faroe Islands\",\"Iles Féro\",\"FO\",\"FRO\",\"298@Islas Georgias del Sur y Sandwich del Sur\",\"South Georgia and the South Sandwich Islands\",\"Géorgie du Sud et les Îles Sandwich du Sud\",\"GS\",\"SGS\",@\"Islas Heard y McDonald\",\"Heard Island and McDonald Islands\",\"Les îles Heard et McDonald\",\"HM\",\"HMD\",@\"Islas Maldivas\",\"Maldives\",\"Maldives\",\"MV\",\"MDV\",\"960@Islas Malvinas\",\"Falkland Islands (Malvinas)\",\"Iles Falkland (Malvinas\",\"FK\",\"FLK\",\"500@Islas Marianas del Norte\",\"Northern Mariana Islands\",\"Iles Mariannes du Nord\",\"MP\",\"MNP\",\"1 670@Islas Marshall\",\"Marshall Islands\",\"Iles Marshall\",\"MH\",\"MHL\",\"692@Islas Pitcairn\",\"Pitcairn Islands\",\"Iles Pitcairn\",\"PN\",\"PCN\",\"870@Islas Salomón\",\"Solomon Islands\",\"Iles Salomon\",\"SB\",\"SLB\",\"677@Islas Turcas y Caicos\",\"Turks and Caicos Islands\",\"Iles Turques et Caïques\",\"TC\",\"TCA\",\"1 649@Islas Ultramarinas Menores de Estados Unidos\",\"United States Minor Outlying Islands\",\"États-Unis Îles mineures éloignées\",\"UM\",\"UMI\",@\"Islas Vírgenes Británicas\",\"Virgin Islands\",\"Iles Vierges\",\"VG\",\"VG\",\"1 284@Islas Vírgenes de los Estados Unidos\",\"United States Virgin Islands\",\"Îles Vierges américaines\",\"VI\",\"VIR\",\"1 340@Israel\",\"Israel\",\"Israël\",\"IL\",\"ISR\",\"972@Italia\",\"Italy\",\"Italie\",\"IT\",\"ITA\",\"39@Jamaica\",\"Jamaica\",\"Jamaïque\",\"JM\",\"JAM\",\"1 876@Japón\",\"Japan\",\"Japon\",\"JP\",\"JPN\",\"81@Jersey\",\"Jersey\",\"Maillot\",\"JE\",\"JEY\",@\"Jordania\",\"Jordan\",\"Jordan\",\"JO\",\"JOR\",\"962@Kazajistán\",\"Kazakhstan\",\"Le Kazakhstan\",\"KZ\",\"KAZ\",\"7@Kenia\",\"Kenya\",\"Kenya\",\"KE\",\"KEN\",\"254@Kirgizstán\",\"Kyrgyzstan\",\"Kirghizstan\",\"KG\",\"KGZ\",\"996@Kiribati\",\"Kiribati\",\"Kiribati\",\"KI\",\"KIR\",\"686@Kuwait\",\"Kuwait\",\"Koweït\",\"KW\",\"KWT\",\"965@Líbano\",\"Lebanon\",\"Liban\",\"LB\",\"LBN\",\"961@Laos\",\"Laos\",\"Laos\",\"LA\",\"LAO\",\"856@Lesoto\",\"Lesotho\",\"Lesotho\",\"LS\",\"LSO\",\"266@Letonia\",\"Latvia\",\"La Lettonie\",\"LV\",\"LVA\",\"371@Liberia\",\"Liberia\",\"Liberia\",\"LR\",\"LBR\",\"231@Libia\",\"Libya\",\"Libye\",\"LY\",\"LBY\",\"218@Liechtenstein\",\"Liechtenstein\",\"Liechtenstein\",\"LI\",\"LIE\",\"423@Lituania\",\"Lithuania\",\"La Lituanie\",\"LT\",\"LTU\",\"370@Luxemburgo\",\"Luxembourg\",\"Luxembourg\",\"LU\",\"LUX\",\"352@México\",\"Mexico\",\"Mexique\",\"MX\",\"MEX\",\"52@Mónaco\",\"Monaco\",\"Monaco\",\"MC\",\"MCO\",\"377@Macao\",\"Macao\",\"Macao\",\"MO\",\"MAC\",\"853@Macedonia\",\"Macedonia\",\"Macédoine\",\"MK\",\"MKD\",\"389@Madagascar\",\"Madagascar\",\"Madagascar\",\"MG\",\"MDG\",\"261@Malasia\",\"Malaysia\",\"Malaisie\",\"MY\",\"MYS\",\"60@Malawi\",\"Malawi\",\"Malawi\",\"MW\",\"MWI\",\"265@Mali\",\"Mali\",\"Mali\",\"ML\",\"MLI\",\"223@Malta\",\"Malta\",\"Malte\",\"MT\",\"MLT\",\"356@Marruecos\",\"Morocco\",\"Maroc\",\"MA\",\"MAR\",\"212@Martinica\",\"Martinique\",\"Martinique\",\"MQ\",\"MTQ\",@\"Mauricio\",\"Mauritius\",\"Iles Maurice\",\"MU\",\"MUS\",\"230@Mauritania\",\"Mauritania\",\"Mauritanie\",\"MR\",\"MRT\",\"222@Mayotte\",\"Mayotte\",\"Mayotte\",\"YT\",\"MYT\",\"262@Micronesia\",\"Estados Federados de\",\"Federados Estados de\",\"FM\",\"FSM\",\"691@Moldavia\",\"Moldova\",\"Moldavie\",\"MD\",\"MDA\",\"373@Mongolia\",\"Mongolia\",\"Mongolie\",\"MN\",\"MNG\",\"976@Montenegro\",\"Montenegro\",\"Monténégro\",\"ME\",\"MNE\",\"382@Montserrat\",\"Montserrat\",\"Montserrat\",\"MS\",\"MSR\",\"1 664@Mozambique\",\"Mozambique\",\"Mozambique\",\"MZ\",\"MOZ\",\"258@Namibia\",\"Namibia\",\"Namibie\",\"NA\",\"NAM\",\"264@Nauru\",\"Nauru\",\"Nauru\",\"NR\",\"NRU\",\"674@Nepal\",\"Nepal\",\"Népal\",\"NP\",\"NPL\",\"977@Nicaragua\",\"Nicaragua\",\"Nicaragua\",\"NI\",\"NIC\",\"505@Niger\",\"Niger\",\"Niger\",\"NE\",\"NER\",\"227@Nigeria\",\"Nigeria\",\"Nigeria\",\"NG\",\"NGA\",\"234@Niue\",\"Niue\",\"Niou\",\"NU\",\"NIU\",\"683@Noruega\",\"Norway\",\"Norvège\",\"NO\",\"NOR\",\"47@Nueva Caledonia\",\"New Caledonia\",\"Nouvelle-Calédonie\",\"NC\",\"NCL\",\"687@Nueva Zelanda\",\"New Zealand\",\"Nouvelle-Zélande\",\"NZ\",\"NZL\",\"64@Omán\",\"Oman\",\"Oman\",\"OM\",\"OMN\",\"968@Países Bajos\",\"Netherlands\",\"Pays-Bas\",\"NL\",\"NLD\",\"31@Pakistán\",\"Pakistan\",\"Pakistan\",\"PK\",\"PAK\",\"92@Palau\",\"Palau\",\"Palau\",\"PW\",\"PLW\",\"680@Palestina\",\"Palestine\",\"La Palestine\",\"PS\",\"PSE\",@\"Panamá\",\"Panama\",\"Panama\",\"PA\",\"PAN\",\"507@Papúa Nueva Guinea\",\"Papua New Guinea\",\"Papouasie-Nouvelle-Guinée\",\"PG\",\"PNG\",\"675@Paraguay\",\"Paraguay\",\"Paraguay\",\"PY\",\"PRY\",\"595@Perú\",\"Peru\",\"Pérou\",\"PE\",\"PER\",\"51@Polinesia Francesa\",\"French Polynesia\",\"Polynésie française\",\"PF\",\"PYF\",\"689@Polonia\",\"Poland\",\"Pologne\",\"PL\",\"POL\",\"48@Portugal\",\"Portugal\",\"Portugal\",\"PT\",\"PRT\",\"351@Puerto Rico\",\"Puerto Rico\",\"Porto Rico\",\"PR\",\"PRI\",\"1@Qatar\",\"Qatar\",\"Qatar\",\"QA\",\"QAT\",\"974@Reino Unido\",\"United Kingdom\",\"Royaume-Uni\",\"GB\",\"GBR\",\"44@República Centroafricana\",\"Central African Republic\",\"République Centrafricaine\",\"CF\",\"CAF\",\"236@República Checa\",\"Czech Republic\",\"République Tchèque\",\"CZ\",\"CZE\",\"420@República Dominicana\",\"Dominican Republic\",\"République Dominicaine\",\"DO\",\"DOM\",\"1 809@Reunión\",\"Réunion\",\"Réunion\",\"RE\",\"REU\",@\"Ruanda\",\"Rwanda\",\"Rwanda\",\"RW\",\"RWA\",\"250@Rumanía\",\"Romania\",\"Roumanie\",\"RO\",\"ROU\",\"40@Rusia\",\"Russia\",\"La Russie\",\"RU\",\"RUS\",\"7@Sahara Occidental\",\"Western Sahara\",\"Sahara Occidental\",\"EH\",\"ESH\",@\"Samoa\",\"Samoa\",\"Samoa\",\"WS\",\"WSM\",\"685@Samoa Americana\",\"American Samoa\",\"Les Samoa américaines\",\"AS\",\"ASM\",\"1 684@San Bartolomé\",\"Saint Barthélemy\",\"Saint-Barthélemy\",\"BL\",\"BLM\",\"590@San Cristóbal y Nieves\",\"Saint Kitts and Nevis\",\"Saint Kitts et Nevis\",\"KN\",\"KNA\",\"1 869@San Marino\",\"San Marino\",\"San Marino\",\"SM\",\"SMR\",\"378@San Martín (Francia)\",\"Saint Martin (French part)\",\"Saint-Martin (partie française)\",\"MF\",\"MAF\",\"1 599@San Pedro y Miquelón\",\"Saint Pierre and Miquelon\",\"Saint-Pierre-et-Miquelon\",\"PM\",\"SPM\",\"508@San Vicente y las Granadinas\",\"Saint Vincent and the Grenadines\",\"Saint-Vincent et Grenadines\",\"VC\",\"VCT\",\"1 784@Santa Elena\",\"Ascensión y Tristán de Acuña\",\"Ascensión y Tristan de Acuña\",\"SH\",\"SHN\",\"290@Santa Lucía\",\"Saint Lucia\",\"Sainte-Lucie\",\"LC\",\"LCA\",\"1 758@Santo Tomé y Príncipe\",\"Sao Tome and Principe\",\"Sao Tomé et Principe\",\"ST\",\"STP\",\"239@Senegal\",\"Senegal\",\"Sénégal\",\"SN\",\"SEN\",\"221@Serbia\",\"Serbia\",\"Serbie\",\"RS\",\"SRB\",\"381@Seychelles\",\"Seychelles\",\"Les Seychelles\",\"SC\",\"SYC\",\"248@Sierra Leona\",\"Sierra Leone\",\"Sierra Leone\",\"SL\",\"SLE\",\"232@Singapur\",\"Singapore\",\"Singapour\",\"SG\",\"SGP\",\"65@Siria\",\"Syria\",\"Syrie\",\"SY\",\"SYR\",\"963@Somalia\",\"Somalia\",\"Somalie\",\"SO\",\"SOM\",\"252@Sri lanka\",\"Sri Lanka\",\"Sri Lanka\",\"LK\",\"LKA\",\"94@Sudáfrica\",\"South Africa\",\"Afrique du Sud\",\"ZA\",\"ZAF\",\"27@Sudán\",\"Sudan\",\"Soudan\",\"SD\",\"SDN\",\"249@Suecia\",\"Sweden\",\"Suède\",\"SE\",\"SWE\",\"46@Suiza\",\"Switzerland\",\"Suisse\",\"CH\",\"CHE\",\"41@Surinám\",\"Suriname\",\"Surinam\",\"SR\",\"SUR\",\"597@Svalbard y Jan Mayen\",\"Svalbard and Jan Mayen\",\"Svalbard et Jan Mayen\",\"SJ\",\"SJM\",@\"Swazilandia\",\"Swaziland\",\"Swaziland\",\"SZ\",\"SWZ\",\"268@Tadjikistán\",\"Tajikistan\",\"Le Tadjikistan\",\"TJ\",\"TJK\",\"992@Tailandia\",\"Thailand\",\"Thaïlande\",\"TH\",\"THA\",\"66@Taiwán\",\"Taiwan\",\"Taiwan\",\"TW\",\"TWN\",\"886@Tanzania\",\"Tanzania\",\"Tanzanie\",\"TZ\",\"TZA\",\"255@Territorio Británico del Océano Índico\",\"British Indian Ocean Territory\",\"Territoire britannique de l'océan Indien\",\"IO\",\"IOT\",@\"Territorios Australes y Antárticas Franceses\",\"French Southern Territories\",\"Terres australes françaises\",\"TF\",\"ATF\",@\"Timor Oriental\",\"East Timor\",\"Timor-Oriental\",\"TL\",\"TLS\",\"670@Togo\",\"Togo\",\"Togo\",\"TG\",\"TGO\",\"228@Tokelau\",\"Tokelau\",\"Tokélaou\",\"TK\",\"TKL\",\"690@Tonga\",\"Tonga\",\"Tonga\",\"TO\",\"TON\",\"676@Trinidad y Tobago\",\"Trinidad and Tobago\",\"Trinidad et Tobago\",\"TT\",\"TTO\",\"1 868@Tunez\",\"Tunisia\",\"Tunisie\",\"TN\",\"TUN\",\"216@Turkmenistán\",\"Turkmenistan\",\"Le Turkménistan\",\"TM\",\"TKM\",\"993@Turquía\",\"Turkey\",\"Turquie\",\"TR\",\"TUR\",\"90@Tuvalu\",\"Tuvalu\",\"Tuvalu\",\"TV\",\"TUV\",\"688@Ucrania\",\"Ukraine\",\"L'Ukraine\",\"UA\",\"UKR\",\"380@Uganda\",\"Uganda\",\"Ouganda\",\"UG\",\"UGA\",\"256@Uruguay\",\"Uruguay\",\"Uruguay\",\"UY\",\"URY\",\"598@Uzbekistán\",\"Uzbekistan\",\"L'Ouzbékistan\",\"UZ\",\"UZB\",\"998@Vanuatu\",\"Vanuatu\",\"Vanuatu\",\"VU\",\"VUT\",\"678@Venezuela\",\"Venezuela\",\"Venezuela\",\"VE\",\"VEN\",\"58@Vietnam\",\"Vietnam\",\"Vietnam\",\"VN\",\"VNM\",\"84@Wallis y Futuna\",\"Wallis and Futuna\",\"Wallis et Futuna\",\"WF\",\"WLF\",\"681@Yemen\",\"Yemen\",\"Yémen\",\"YE\",\"YEM\",\"967@Yibuti\",\"Djibouti\",\"Djibouti\",\"DJ\",\"DJI\",\"253@Zambia\",\"Zambia\",\"Zambie\",\"ZM\",\"ZMB\",\"260@Zimbabue\",\"Zimbabwe\",\"Zimbabwe\",\"ZW\",\"ZWE\",\"263\"";
        String[] countryArray = cal.split("@");

        String africa = "Angola\n" +
                "Argelia\n" +
                "Benín\n" +
                "Botsuana\n" +
                "Burkina Faso\n" +
                "Burundi\n" +
                "Cabo Verde\n" +
                "Camerún\n" +
                "Chad\n" +
                "Comoras\n" +
                "Congo\n" +
                "República Democrática del Congo\n" +
                "Costa de Marfil\n" +
                "Egipto\n" +
                "Eritrea\n" +
                "Etiopía\n" +
                "Gabón\n" +
                "Gambia\n" +
                "Ghana\n" +
                "Guinea\n" +
                "Guinea Ecuatorial\n" +
                "Guinea-Bissau\n" +
                "Santa Elena, Ascensión y Tristán de Acuña\n" +
                "Kenia\n" +
                "Lesoto\n" +
                "Liberia\n" +
                "Libia\n" +
                "Madagascar\n" +
                "Malawi\n" +
                "Mali\n" +
                "Marruecos\n" +
                "Mauricio\n" +
                "Mauritania\n" +
                "Mayotte\n" +
                "Mozambique\n" +
                "Namibia\n" +
                "Niger\n" +
                "Nepal\n" +
                "Nigeria\n" +
                "República Centroafricana\n" +
                "Reunión\n" +
                "Ruanda\n" +
                "Santo Tomé y Príncipe\n" +
                "Senegal\n" +
                "Seychelles\n" +
                "Sierra Leona\n" +
                "Somalia\n" +
                "Sahara Occidental\n" +
                "Sudáfrica\n" +
                "Sudán\n" +
                "Sudán del Sur\n" +
                "Suazilandia\n" +
                "Tanzania\n" +
                "Togo\n" +
                "Tunez\n" +
                "Uganda\n" +
                "Yibuti\n" +
                "Zambia\n" +
                "Zimbabue";

        String asia = "Afganistán\n" +
                "Arabia Saudita\n" +
                "Armenia\n" +
                "Azerbayán\n" +
                "Baréin\n" +
                "Bangladesh\n" +
                "Birmania\n" +
                "Bután\n" +
                "Brunéi\n" +
                "Camboya\n" +
                "China\n" +
                "Corea del Norte\n" +
                "Corea del Sur\n" +
                "Emiratos Árabes Unidos\n" +
                "Filipinas\n" +
                "Hong Kong\n" +
                "India\n" +
                "Indonesia\n" +
                "Iraq\n" +
                "Irán\n" +
                "Israel\n" +
                "Japón\n" +
                "Jordania\n" +
                "Kazajistán\n" +
                "Kirgizstán\n" +
                "Kuwait\n" +
                "Laos\n" +
                "Líbano\n" +
                "Macao\n" +
                "Malasia\n" +
                "Maldivas\n" +
                "Mongolia\n" +
                "Omán\n" +
                "Pakistán\n" +
                "Palestina\n" +
                "Qatar\n" +
                "Rusia\n" +
                "Singapur\n" +
                "Siria\n" +
                "Sri Lanka\n" +
                "Tailandia\n" +
                "Tadjikistán\n" +
                "Turkmenistán\n" +
                "Turquía\n" +
                "Uzbekistán\n" +
                "Vietnam\n" +
                "Yemen";

        String europa = "Acrotiri y Dhekelia\n" +
                "Albania\n" +
                "Alemania\n" +
                "Andorra\n" +
                "Austria\n" +
                "Bielorrusia\n" +
                "Bélgica\n" +
                "Bosnia y Herzegovina\n" +
                "Bulgaria\n" +
                "Ciudad del Vaticano\n" +
                "Chipre\n" +
                "Croacia\n" +
                "Dinamarca\n" +
                "Eslovaquia\n" +
                "Eslovenia\n" +
                "España\n" +
                "Estonia\n" +
                "Finlandia\n" +
                "Francia\n" +
                "Georgia\n" +
                "Gibraltar\n" +
                "Grecia\n" +
                "Guernsey\n" +
                "Hungría\n" +
                "Irlanda\n" +
                "Islandia\n" +
                "Italia\n" +
                "Letonia\n" +
                "Liechtenstein\n" +
                "Lituania\n" +
                "Luxemburgo\n" +
                "Malta\n" +
                "Mónaco\n" +
                "Macedonia\n" +
                "Moldavia\n" +
                "Montenegro\n" +
                "Noruega\n" +
                "Países Bajos\n" +
                "Polonia\n" +
                "Portugal\n" +
                "Reino Unido\n" +
                "Gales\n" +
                "Inglaterra\n" +
                "Escocia\n" +
                "Irlanda del Norte\n" +
                "República Checa\n" +
                "Rumanía\n" +
                "San Marino\n" +
                "Serbia\n" +
                "Suecia\n" +
                "Suiza\n" +
                "Ucrania";

        String oceania = "Australia\n" +
                "Isla de Navidad\n" +
                "Islas Marshall\n" +
                "Islas Pitcairn\n" +
                "Islas Salomón\n" +
                "Micronesia\n" +
                "Fiyi\n" +
                "Guam\n" +
                "Kiribati\n" +
                "Nauru\n" +
                "Nueva Zelanda\n" +
                "Palau\n" +
                "Papúa Nueva Guinea\n" +
                "Nueva Caledonia\n" +
                "Samoa\n" +
                "Tonga\n" +
                "Tuvalu\n" +
                "Vanuatu\n" +
                "Wallis y Futuna";

        String others = "Bahamas\n" +
                "Barbados\n" +
                "Belize\n" +
                "Islas Bermudas\n" +
                "Bolivia\n" +
                "Cayman Islands\n" +
                "Costa Rica\n" +
                "Curacao\n" +
                "Dominica\n" +
                "República Dominicana\n" +
                "Ecuador\n" +
                "El Salvador\n" +
                "French Guiana\n" +
                "Grenada\n" +
                "Guadeloupe\n" +
                "Guatemala\n" +
                "Guyana\n" +
                "Haiti\n" +
                "Honduras\n" +
                "Jamaica\n" +
                "Nicaragua\n" +
                "Panama\n" +
                "Puerto Rico\n" +
                "St. Eustatius\n" +
                "Santa Elena\n" +
                "Santa Lucía\n" +
                "Suriname\n";

        for (String line : countryArray) {
            String[] countryTemp = line.split(",");
            Country country = new Country();

            String spanishName = countryTemp[0].replaceAll("\"", "");
            String englishName = countryTemp[1].replaceAll("\"", "");
            country.setSpanishName(spanishName != null ? spanishName : englishName);
            country.setEnglishName(englishName != null ? englishName : spanishName);
            country.setCode(countryTemp[3].replaceAll("\"", ""));

            String[] africaArray = africa.split("\n");
            String[] oceaniaArray = oceania.split("\n");
            String[] asiaArray = asia.split("\n");
            String[] europaArray = europa.split("\n");
            String[] othersArray = others.split("\n");

            if (isIn(country, africaArray) || country.getSpanishName().equals("Cuba") || country.getSpanishName().equals("Irán")
                    || country.getSpanishName().equals("Iraq") || country.getSpanishName().equals("Turquía")
                    || country.getSpanishName().equals("Tunez")) {
                country.setGroup("7");
                country.setPriceList(dhlPrices("7"));
            } else if (isIn(country, oceaniaArray) || isIn(country, asiaArray)) {
                country.setGroup("6");
                country.setPriceList(dhlPrices("6"));
            } else if (isIn(country, europaArray)) {
                country.setGroup("5");
                country.setPriceList(dhlPrices("5"));
            } else if (isIn(country, othersArray)) {
                country.setGroup("4");
                country.setPriceList(dhlPrices("4"));
            } else if (country.getSpanishName().equals("Argentina")) {
                country.setGroup("1");
                country.setPriceList(dhlPrices("1"));
            } else if (country.getSpanishName().equals("Paraguay") || country.getSpanishName().equals("Brasil")
                    || country.getSpanishName().equals("Chile")) {
                country.setGroup("2");
                country.setPriceList(dhlPrices("2"));
            } else if (country.getSpanishName().equals("Canadá") || country.getSpanishName().equals("México")
                    || country.getSpanishName().equals("Estados Unidos de América")) {
                country.setGroup("3");
                country.setPriceList(dhlPrices("3"));
            }
            if (country.getGroup() == null) {
                country.setGroup("9");
                country.setPriceList(dhlPrices("9"));
            }

            mongoTemplate.save(country);
        }
    }

    @ChangeSet(order = "03", author = "initiator", id = "03-addUser")
    public void addUser(MongoTemplate mongoTemplate) {
        User user = new User();
        user.setFirstName("Administrator");
        user.setLastName("Admin");
        user.setUserName("admin");
        user.setEmail("admin@localhost");
        user.setPhone("+5 55555555");
        user.setCp("1000");
        user.setCity("Montevideo");
        user.setLine1("Address Address Address");
        user.setVisible(true);

        Query query = new Query();
        query.addCriteria(Criteria.where("spanish_name").is("Uruguay"));
        Country country = mongoTemplate.findOne(query, Country.class);

        user.setCountry(country);
        user.setActivated(true);
        user.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        user.setLangKey("en");
        user.setCreatedBy("system");
        user.setCreatedDate(Instant.now());
        user.setAuthorities(new HashSet<>(mongoTemplate.findAll(Authority.class)));

        mongoTemplate.save(user);
    }

    @ChangeSet(order = "04", author = "initiator", id = "04-addClassification")
    public void addClassification(MongoTemplate mongoTemplate) {
        Classification classification = new Classification();
        classification.setName("classification.new");
        classification.setCreatedBy("system");
        classification.setCreatedDate(Instant.now());
        mongoTemplate.save(classification);

        Classification classificationTwo = new Classification();
        classificationTwo.setName("classification.used");
        classificationTwo.setCreatedBy("system");
        classificationTwo.setCreatedDate(Instant.now());
        mongoTemplate.save(classificationTwo);
    }

    private boolean isIn(Country country, String[] array) {
        for (String val : array) {
            if (val.equals(country.getSpanishName()) || val.equals(country.getEnglishName()))
                return true;
        }

        return false;
    }

    private List<DhlPrice> dhlPrices(String group) {
        List<DhlPrice> result = new ArrayList<>();


        switch (group) {
            case "1":
                createDhlList("1", result);
                break;
            case "2":
                createDhlList("2", result);
                break;
            case "3":
                createDhlList("3", result);
                break;
            case "4":
                createDhlList("4", result);
                break;
            case "5":
                createDhlList("5", result);
                break;
            case "6":
                createDhlList("6", result);
                break;
            case "7":
                createDhlList("7", result);
                break;
            case "9":
                createDhlList("9", result);
                break;
        }
        return result;
    }

    private List<DhlPrice> createDhlList(String group, List<DhlPrice> result) {
        String kgs = "1.0\n" +
                "2.0\n" +
                "3.0\n" +
                "4.0\n" +
                "5.0\n" +
                "6.0\n" +
                "7.0\n" +
                "8.0\n" +
                "9.0\n" +
                "10.0\n" +
                "11.0\n" +
                "12.0\n" +
                "13.0\n" +
                "14.0\n" +
                "15.0\n" +
                "16.0\n" +
                "17.0\n" +
                "18.0\n" +
                "19.0\n" +
                "20.0\n";
        String[] kgArray = kgs.split("\n");

        String group1 = "10.31\n" +
                "12.81\n" +
                "14.68\n" +
                "15.94\n" +
                "17.20\n" +
                "18.60\n" +
                "20.00\n" +
                "21.40\n" +
                "22.80\n" +
                "24.20\n" +
                "25.62\n" +
                "27.04\n" +
                "28.46\n" +
                "29.88\n" +
                "31.30\n" +
                "32.72\n" +
                "34.14\n" +
                "35.56\n" +
                "36.98\n" +
                "38.40\n";
        String[] group1Array = group1.split("\n");

        String group2 = "16.90\n" +
                "19.90\n" +
                "22.89\n" +
                "25.87\n" +
                "28.85\n" +
                "31.91\n" +
                "34.97\n" +
                "38.03\n" +
                "41.09\n" +
                "44.15\n" +
                "46.91\n" +
                "49.67\n" +
                "52.43\n" +
                "55.19\n" +
                "57.95\n" +
                "60.71\n" +
                "63.47\n" +
                "66.23\n" +
                "68.99\n" +
                "71.75\n";
        String[] group2Array = group2.split("\n");

        String group3 = "17.35\n" +
                "20.27\n" +
                "23.25\n" +
                "26.29\n" +
                "29.33\n" +
                "32.33\n" +
                "35.33\n" +
                "38.33\n" +
                "41.33\n" +
                "44.33\n" +
                "47.35\n" +
                "50.37\n" +
                "53.39\n" +
                "56.41\n" +
                "59.43\n" +
                "62.45\n" +
                "65.47\n" +
                "68.49\n" +
                "71.51\n" +
                "74.53\n";
        String[] group3Array = group3.split("\n");

        String group4 = "17.98\n" +
                "21.28\n" +
                "24.65\n" +
                "28.09\n" +
                "31.53\n" +
                "34.87\n" +
                "38.21\n" +
                "41.55\n" +
                "44.89\n" +
                "48.23\n" +
                "51.55\n" +
                "54.87\n" +
                "58.19\n" +
                "61.51\n" +
                "64.83\n" +
                "68.15\n" +
                "71.47\n" +
                "74.79\n" +
                "78.11\n" +
                "81.43\n";
        String[] group4Array = group4.split("\n");

        String group5 = "18.71\n" +
                "22.57\n" +
                "26.43\n" +
                "30.35\n" +
                "34.27\n" +
                "37.91\n" +
                "41.55\n" +
                "45.19\n" +
                "48.83\n" +
                "52.47\n" +
                "56.15\n" +
                "59.83\n" +
                "63.51\n" +
                "67.19\n" +
                "70.87\n" +
                "74.55\n" +
                "78.23\n" +
                "81.91\n" +
                "85.59\n" +
                "89.27\n";
        String[] group5Array = group5.split("\n");

        String group6 = "19.72\n" +
                "24.10\n" +
                "28.41\n" +
                "32.75\n" +
                "37.09\n" +
                "41.43\n" +
                "45.77\n" +
                "50.11\n" +
                "54.45\n" +
                "58.79\n" +
                "63.17\n" +
                "67.55\n" +
                "71.93\n" +
                "76.31\n" +
                "80.69\n" +
                "85.07\n" +
                "89.45\n" +
                "93.83\n" +
                "98.21\n" +
                "102.59\n";
        String[] group6Array = group6.split("\n");

        String group7 = "26.96\n" +
                "33.00\n" +
                "38.77\n" +
                "44.35\n" +
                "49.93\n" +
                "54.93\n" +
                "59.93\n" +
                "64.93\n" +
                "69.93\n" +
                "74.93\n" +
                "79.91\n" +
                "84.89\n" +
                "89.87\n" +
                "94.85\n" +
                "99.83\n" +
                "104.81\n" +
                "109.79\n" +
                "114.77\n" +
                "119.75\n" +
                "124.73\n";
        String[] group7Array = group7.split("\n");

        double minKg = 0;
        for (int i = 0; i < kgArray.length; i++) {
            DhlPrice dhlPrice = new DhlPrice();
            dhlPrice.setMinKg(minKg);
            dhlPrice.setMaxKg(Double.valueOf(kgArray[i]));
            minKg = dhlPrice.getMaxKg();

            if (group.equals("1"))
                dhlPrice.setPrice(Double.valueOf(group1Array[i]));
            else if (group.equals("2"))
                dhlPrice.setPrice(Double.valueOf(group2Array[i]));
            else if (group.equals("3"))
                dhlPrice.setPrice(Double.valueOf(group3Array[i]));
            else if (group.equals("4"))
                dhlPrice.setPrice(Double.valueOf(group4Array[i]));
            else if (group.equals("5"))
                dhlPrice.setPrice(Double.valueOf(group5Array[i]));
            else if (group.equals("6"))
                dhlPrice.setPrice(Double.valueOf(group6Array[i]));
            else if (group.equals("7"))
                dhlPrice.setPrice(Double.valueOf(group7Array[i]));
            else if (group.equals("9"))
                dhlPrice.setPrice(0);

            result.add(dhlPrice);
        }

        return result;
    }

    @ChangeSet(order = "05", author = "initiator", id = "05-addUIData")
    public void ddUIData(MongoTemplate mongoTemplate) {
        UIData data = new UIData();
        data.setNameEnglish("LIDERLAF BOOKS");
        data.setNameSpanish("LIDERLAF LIBROS");
        data.setPhone("+5 5255665544");
        data.setMainTextSpanish("<font size=\"5\">Libros nuevos, usados, raros y antiguos. Revistas. Con envíos a todo el mundo.</font>");
        data.setMainTextEnglish("<font size=\"5\">New, used, rare and old books. Magazines. With shipments to the whole world.</font>");
        data.setEmail("liderlaf@gmail.com");
        data.setAddress("Paysandú 1838 e/ Dr Tristan Narvaja y Av. Daniel Fernández Crespo.");
        data.setTwitter("https://www.twitter.com");
        data.setFacebook("https://www.facebook.com");
        data.setInstagram("https://www.instagram.com");
        data.setGoogle("https://www.google.com");
        data.setShipmentPercent(25);
        mongoTemplate.save(data);
    }
}
