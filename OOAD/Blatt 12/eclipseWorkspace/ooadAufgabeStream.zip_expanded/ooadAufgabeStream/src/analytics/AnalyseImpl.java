package analytics;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.DoubleSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entities.Modul;
import entities.Pruefung;
import entities.Student;

public class AnalyseImpl implements Analyse {

	@Override
	public void zeigePruefungenVon(Stream<Pruefung> pruefungen, int mat) {
		pruefungen.filter(pruefung -> pruefung.getStudent().getMatnr() == mat)
				.forEach(
						pruefung ->
							System.out.printf("%s: %d %d.Versuch\n",
								pruefung.getModul().getTitel(),
								pruefung.getNote(),
								pruefung.getVersuch())
						);
	}

	@Override
	public void zeigeBestandenePruefungenVon(Stream<Pruefung> pruefungen, int mat) {
		this.zeigePruefungenVon(pruefungen.filter(pruefung -> pruefung.getNote() < 500), mat);

	}

	@Override
	public void zeigeGeordnetPruefungenVon(Stream<Pruefung> pruefungen, int mat) {
		pruefungen.filter(pruefung -> pruefung.getStudent().getMatnr() == mat)
				.collect(Collectors.groupingBy(Pruefung::getModul)).entrySet().stream()
				.forEach(
						entry ->
							System.out.printf("%s: %s\n",
									entry.getKey().getTitel(),
									entry.getValue().stream()
									.map(pruefung -> String.valueOf(pruefung.getNote()))
									.collect(Collectors.joining(" "))
									)
							);
	}

	@Override
	public void zeigeDetailgeordnetPruefungenVon(Stream<Pruefung> pruefungen, int mat) {

		pruefungen.filter(pruefung -> pruefung.getStudent().getMatnr() == mat)
				.collect(Collectors.groupingBy(Pruefung::getModul)).entrySet().stream()
				.forEach(
						entry ->
							System.out.printf("%s: %s\n",
									entry.getKey().getTitel(),
									entry.getValue().stream()
									.map(Pruefung::getNote)
									.sorted()
									.map(note -> String.valueOf(note))
									.collect(Collectors.joining(" "))
									)
							);
	}

	@Override
	public long anzahlPruefungen(Stream<Pruefung> pruefungen) {
		return pruefungen.count();
	}

	@Override
	public long anzahlBestandenerPruefungen(Stream<Pruefung> pruefungen, int mat) {
		return pruefungen.filter(pruefung -> pruefung.getStudent().getMatnr() == mat)
				.filter(pruefung -> pruefung.getNote() < 500).count();
	}

	@Override
	public double durchschnittsnote(Stream<Pruefung> pruefungen, int mat) {
		return pruefungen.filter(pruefung -> pruefung.getStudent().getMatnr() == mat).mapToDouble(Pruefung::getNote)
				.filter(d -> d < 500).average().orElseGet(() -> 0);
	}

	@Override
	public double durchschnittModul(Stream<Pruefung> pruefungen, String modul) {
		return pruefungen.filter(pruefung -> pruefung.getModul().getTitel().equals(modul))
				.mapToDouble(pruefung -> pruefung.getNote()).average().orElseGet(() -> 0);
	}

	@Override
	public List<Integer> endgueltigDurchgefallen(Stream<Pruefung> pruefungen) {
		return pruefungen.filter(pruefung -> pruefung.getVersuch() == 3).filter(pruefung -> pruefung.getNote() >= 500)
				.map(Pruefung::getStudent).map(Student::getMatnr).distinct().collect(Collectors.toList());
	}

	@Override
	public List<Integer> hatNochOffenenDrittenVersuch(Stream<Pruefung> pruefungen) {
		return pruefungen.collect(Collectors.groupingBy(Pruefung::getModul))
		.values()
		.stream()
		.map(
				pruefungenProModul->pruefungenProModul.stream()
				.collect(Collectors.groupingBy(Pruefung::getStudent))
				.entrySet()
				.stream()
				.filter(
						pruefungenProStudent->
						pruefungenProStudent.getValue().stream().noneMatch(pruefung->pruefung.getVersuch()==3) &&
						pruefungenProStudent.getValue().stream().anyMatch(pruefung->pruefung.getVersuch()==2&&pruefung.getNote()>=500)
						)
				.map(Map.Entry::getKey)
				.map(Student::getMatnr)
				)
		.flatMap(v->v)
		.collect(Collectors.toList());
		
	}

}
