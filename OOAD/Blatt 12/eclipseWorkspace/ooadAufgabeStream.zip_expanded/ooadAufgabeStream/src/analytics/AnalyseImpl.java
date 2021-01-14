package analytics;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entities.Modul;
import entities.Pruefung;

public class AnalyseImpl implements Analyse {

	@Override
	public void zeigePruefungenVon(Stream<Pruefung> pruefungen, int mat) {
		pruefungen.filter(pruefung -> pruefung.getStudent().getMatnr() == mat)
				.forEach(pruefung -> System.out.println(pruefung));
	}

	@Override
	public void zeigeBestandenePruefungenVon(Stream<Pruefung> pruefungen, int mat) {

		pruefungen.filter(pruefung -> pruefung.getNote() < 500)
				.filter(pruefung -> pruefung.getStudent().getMatnr() == mat)
				.forEach(pruefung -> System.out.println(pruefung));

	}

	@Override
	public void zeigeGeordnetPruefungenVon(Stream<Pruefung> pruefungen, int mat) {

		Stream<Modul> module = pruefungen.filter(pruefung -> pruefung.getStudent().getMatnr() == mat)
				.map(pruefung -> pruefung.getModul()).distinct()
				.sorted((m1, m2) -> m1.getTitel().compareTo(m2.getTitel()));
		module.forEach(modul -> {
			System.out.print(modul.getTitel() + ": ");
			System.out.println(pruefungen.filter(pruefung -> pruefung.get))
		});

	}

	@Override
	public void zeigeDetailgeordnetPruefungenVon(Stream<Pruefung> pruefungen, int mat) {
		// TODO Auto-generated method stub

	}

	@Override
	public long anzahlPruefungen(Stream<Pruefung> pruefungen) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long anzahlBestandenerPruefungen(Stream<Pruefung> pruefungen, int mat) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double durchschnittsnote(Stream<Pruefung> pruefungen, int mat) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double durchschnittModul(Stream<Pruefung> pruefungen, String modul) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> endgueltigDurchgefallen(Stream<Pruefung> pruefungen) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> hatNochOffenenDrittenVersuch(Stream<Pruefung> pruefungen) {
		// TODO Auto-generated method stub
		return null;
	}

}
