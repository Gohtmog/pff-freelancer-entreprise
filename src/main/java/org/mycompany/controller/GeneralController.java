package org.mycompany.controller;

import java.util.List;

import org.mycompany.model.CV;
import org.mycompany.model.Candidat;
import org.mycompany.model.Entreprise;
import org.mycompany.model.Notes;
import org.mycompany.model.NotesEntreprise;
import org.mycompany.model.Projet;
import org.mycompany.repo.ICVRepository;
import org.mycompany.repo.ICandidatRepository;
import org.mycompany.repo.IEntrepriseRepository;
import org.mycompany.repo.INotesEntrepriseRepository;
import org.mycompany.repo.INotesRepository;
import org.mycompany.repo.IProjetRepository;
import org.mycompany.repo.ITestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {

	@Autowired
	ICandidatRepository icr;

	@Autowired
	IProjetRepository ipr;

	@Autowired
	ICVRepository icvr;

	@Autowired
	IEntrepriseRepository ier;

	@Autowired
	INotesRepository inr;
	
	@Autowired
	INotesEntrepriseRepository iner;

	@Autowired
	ITestRepository itr;

	public void lienProjetCandidat(Projet pro, Candidat candidat) {
		List<Projet> lP = candidat.getListeProjet();
		List<Candidat> lC = pro.getListeCandidats();

		if (!(lP.contains(pro))) {
			lP.add(pro);
		} else {
			System.out.println("Le projet est déjà enregistré chez le candidat.");
		}

		if (!(lC.contains(candidat))) {
			lC.add(candidat);
		} else {
			System.out.println("Le candidat est déjà enregistré pour ce projet.");
		}

		candidat.setListeProjet(lP);
		pro.setListeCandidats(lC);
		System.out.println("On a mis à jour projet et/ou candidat.");

	}

	public void lienCVCandidat(CV cv, Candidat candidat) {
		List<CV> lCV = candidat.getListeCV();

		if (!(lCV.contains(cv))) {
			lCV.add(cv);
		} else {
			System.out.println("Le CV est déjà enregistré chez le candidat.");
		}

		if (cv.getCandidat() == null) {
			cv.setCandidat(candidat);
		} else {
			System.out.println("Le candidat est déjà enregistré pour ce CV.");
		}

		candidat.setListeCV(lCV);
		System.out.println("On a mis à jour CV et/ou candidat.");

	}

	public void lienNoteCandidat(Notes notes, Candidat candidat) {
		List<Notes> lN = candidat.getListeNotes();

		if (!(lN.contains(notes))) {
			lN.add(notes);
		} else {
			System.out.println("La note est déjà enregistrée chez le candidat.");
		}

		if (notes.getCandidat() == null) {
			notes.setCandidat(candidat);
		} else {
			System.out.println("Le candidat est déjà enregistré pour cette note.");
		}

		candidat.setListeNotes(lN);
		System.out.println("On a mis à jour notes et/ou candidat.");

	}

	
	@PutMapping("/lierEntrepriseNotesEntreprise/{idNote}/{idEnt}")
	public Entreprise lierEntrepriseNotesEntreprise(@PathVariable int idNote, @PathVariable int idEnt) {
		List<NotesEntreprise> listeN = ier.findById(idEnt).get().getlisteNotesEntreprise();
		listeN.add(iner.findById(idNote).get());
		Entreprise newEnt = ier.findById(idEnt).get();
		newEnt.setlisteNotesEntreprise(listeN);
		return ier.save(newEnt);
	}


	
	@PutMapping("/lierEntrepriseProjets/{idPro}/{idEnt}")
	public Entreprise lierEntrepriseProjet(@PathVariable int idPro, @PathVariable int idEnt) {
		List<Projet> listeP = ier.findById(idEnt).get().getListeProjets();
		listeP.add(ipr.getById(idPro));
		Entreprise newEnt = ier.findById(idEnt).get();
		newEnt.setListeProjets(listeP);
		return ier.save(newEnt);
	}

	@PutMapping("/lierCandidatProjets/{idPro}/{idCan}")
	public Candidat lierCandidatProjet(@PathVariable int idPro, @PathVariable int idCan) {
		List<Projet> listeP = icr.findById(idCan).get().getListeProjet();
		listeP.add(ipr.getById(idPro));
		Candidat newCan2 = icr.findById(idCan).get();
		newCan2.setListeProjet(listeP);
		return icr.save(newCan2);

	}

	@PutMapping("/lierProjetsCandidat/{idPro}/{idCan}")
	public Projet lierProjetCandidat(@PathVariable int idPro, @PathVariable int idCan) {
		List<Candidat> listeC = ipr.findById(idPro).get().getListeCandidats();
		listeC.add(icr.getById(idCan));
		Projet newProjet = ipr.findById(idPro).get();
		newProjet.setListeCandidats(listeC);
		return ipr.save(newProjet);

	}

}
