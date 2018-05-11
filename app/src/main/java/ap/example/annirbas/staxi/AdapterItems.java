package ap.example.annirbas.staxi;

public class AdapterItems {

    public  String date_demande;
    public  String heure_demande;
    public  String date_depart;
    public  String heure_depart;
    public  String Sex_tax_pref;
    public  String Tp_cou_pref;
    public  String consultaion;
    public  String Observation;


    //for news details
    public    AdapterItems( String date_demande,String heure_demande,String date_depart,String heure_depart,String Sex_tax_pref, String Tp_cou_pref
           ,  String consultaion, String Observation)
    {

        this. date_demande=date_demande;
        this. heure_demande=heure_demande;

        this. date_depart=date_depart;
        this. heure_depart=heure_depart;
        this. Sex_tax_pref=Sex_tax_pref;
        this. Tp_cou_pref=Tp_cou_pref;
        this. consultaion=consultaion;
        this. Observation=Observation;


    }

}
