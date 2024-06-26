// ============================================================================
// BRAINTRIBE TECHNOLOGY GMBH - www.braintribe.com
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2018 - All Rights Reserved
// It is strictly forbidden to copy, modify, distribute or use this code without written permission
// To this file the Braintribe License Agreement applies.
// ============================================================================


package com.braintribe.build.ant.tasks.extractor.fileset;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;

import com.braintribe.build.ant.types.FileSetTarget;
import com.braintribe.devrock.mc.api.commons.PartIdentifications;
import com.braintribe.logging.Logger;
import com.braintribe.model.artifact.PartTuple;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.artifact.consumable.Part;
import com.braintribe.model.artifact.essential.PartIdentification;
import com.braintribe.model.resource.FileResource;
import com.braintribe.model.resource.Resource;

/**
 * a processor that handles n FileSetTargets <br/>
 * can be used to:<br/>
 * store all {@link FileSetTarget} specified to the {@link MalaclypseLocalDependencyWalkTask}<br/>
 * automatically parse the types specified as {@link String} and convert them into {@link List} of {@link PartTuple}<br/>
 * automatically attach matching {@link Part} to the appropriate {@link FileSet} <br/>
 * export all {@link FileSet} to the {@link Project} under the id as specified in the {@link FileSetTarget}
 * 
 * 
 * @author Pit
 *
 */
public class FileSetTargetProcessor {

	private static Logger log = Logger.getLogger(FileSetTargetProcessor.class);
	
	public static enum STANDARD_FILESETS { JAR, SOURCES, JAVADOC}
	
	private final List<FileSetTarget> targets = new ArrayList<FileSetTarget>();
	private Map<FileSet, String> fileSetToIdMap;
	private Map<FileSet, Integer> fileSetToSizeMap;
	private Map<String, FileSet> idToFilesetMap;
	private Map<String, String> idToPathIdMap;
	private Map<List<PartIdentification>, List<FileSet>> partTuplesToFileSetMap;
	private File directory;
	private Project project;
	private final String directoryDelimiter = System.getProperty( "file.separator");
	
	/**
	 * sets the base directory for ALL filesets 
	 * @param directory - the {@link File} that points to the base directory, usually M2_REPO
	 */
	public void setDirectory(File directory) {
		this.directory = directory;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	
	/**
	 * adds a FileSetTarget to the list of file sets 
	 * @param target - the {@link FileSetTarget} to add 
	 */
	public void addTarget( FileSetTarget target) {
		targets.add( target);
	}
	
	
	/**
	 * sets up the target processor : generates the internal structure for all declared file set targets, and 
	 * adds the intrinsic ones (classpath file set, sources file set - if set, javadoc file set - if set
	 * @param classpathFilesetId - the {@link String} that is the id for the classpath (default) file set
	 * @param sourcesFilesetId - the {@link String} that is the optional id for the sources file set
	 * @param javadocFilesetId - the {@link String} that is the optional id for the javadoc file set 	
	 */
	public void setupWithStandardFilesetIds(String classpathFilesetId, String sourcesFilesetId, String javadocFilesetId, String classpathTypeProperty, String classpathId) {
	
		fileSetToIdMap = new HashMap<>();
		partTuplesToFileSetMap = new HashMap<>();
		fileSetToSizeMap = new HashMap<>();
		idToFilesetMap = new HashMap<>();
		idToPathIdMap = new HashMap<>();
	
		for (FileSetTarget target : targets) {
			String id = target.getId();
			String types = target.getType();
			List<PartIdentification> relevantPartTuples = RelevantPartTupleProcessor.parseRelevantPartTuplesFromString(types);
			FileSet fileSet = new FileSet();
			fileSet.setDir( directory);
			fileSet.setProject(project);
			fileSetToIdMap.put( fileSet, id);
			idToFilesetMap.put( id, fileSet);
			List<FileSet> filesets = partTuplesToFileSetMap.computeIfAbsent(relevantPartTuples, r -> new ArrayList<>());
			filesets.add( fileSet);
			
			// path
			String path = target.getPath();
			if (path != null) {
				idToPathIdMap.put( id, path);
			}
		}
		//
		// automatically add the three file set targets
		//
		
		// jar - now, optional as well 
		if (classpathFilesetId != null) {
			buildClasspathFileset(classpathFilesetId, classpathTypeProperty);
			//idToPathIdMap.put( classpathFilesetId, classpathFilesetId);
		}
		
		if (classpathId != null) {
			buildClasspathFileset(classpathId, classpathTypeProperty);			
			idToPathIdMap.put( classpathId, classpathId);
		}
		
		// sources - optional
		if (sourcesFilesetId != null) {
			FileSet sourcesFileSet = new FileSet();
			sourcesFileSet.setDir( directory);
			sourcesFileSet.setProject(project);
			fileSetToIdMap.put( sourcesFileSet, sourcesFilesetId);
			idToFilesetMap.put( sourcesFilesetId, sourcesFileSet); 
			List<PartIdentification> sourcesPartTuples = Collections.singletonList(PartIdentifications.sources_jar);
			List<FileSet> filesets = partTuplesToFileSetMap.computeIfAbsent(sourcesPartTuples, r -> new ArrayList<>());
			filesets.add( sourcesFileSet);
			//partTuplesToFileSetMap.put( sourcesPartTuples, sourcesFileSet);
				
		}
		
		// javadoc - optional
		if (javadocFilesetId != null) {
			FileSet javadocFileSet = new FileSet();
			javadocFileSet.setDir( directory);
			javadocFileSet.setProject(project);
			fileSetToIdMap.put( javadocFileSet, javadocFilesetId);
			idToFilesetMap.put( javadocFilesetId, javadocFileSet); 
			List<PartIdentification> javadocPartTuples = Collections.singletonList(PartIdentifications.javadoc_jar);
			List<FileSet> filesets = partTuplesToFileSetMap.computeIfAbsent( javadocPartTuples, r -> new ArrayList<>());
			filesets.add( javadocFileSet);	
		}
		
		//
		// sanity check - at least one file set target should be defined by now
		//
		if (idToFilesetMap.size() == 0) {
			// PGA I commented this warn out as I have a use-case (writing solutions-list file only) where this happens and everything works fine
			// log.warn( "no file set targets defined : is this intended? injecting @injected.fileset");

			// inject default from the type setting here 
			FileSet classpathFileSet = new FileSet();
			classpathFileSet.setDir( directory);
			classpathFileSet.setProject(project);
			classpathFilesetId = "injected.fileset";
			fileSetToIdMap.put( classpathFileSet, classpathFilesetId);
			idToFilesetMap.put( classpathFilesetId, classpathFileSet); 
			List<PartIdentification> classpathPartTuples = Collections.singletonList(PartIdentifications.jar);
			// if any additional types are set for the classpath file set, parse and add them to its relevant part tuples
			if (classpathTypeProperty != null) {
				List<PartIdentification> specifiedPartTuples = RelevantPartTupleProcessor.parseRelevantPartTuplesFromString(classpathTypeProperty);
				classpathPartTuples = RelevantPartTupleProcessor.mergeRelevantPartTuplesForEnricher(classpathPartTuples, specifiedPartTuples);
			}
			List<FileSet> filesets = partTuplesToFileSetMap.computeIfAbsent( classpathPartTuples, r -> new ArrayList<>());
			filesets.add( classpathFileSet);	
			//partTuplesToFileSetMap.put( classpathPartTuples, classpathFileSet);
			
		}
	}

	private void buildClasspathFileset(String classpathFilesetId, String classpathTypeProperty) {
		FileSet classpathFileSet = new FileSet();
		classpathFileSet.setDir( directory);
		classpathFileSet.setProject(project);
		fileSetToIdMap.put( classpathFileSet, classpathFilesetId);
		idToFilesetMap.put( classpathFilesetId, classpathFileSet); 
		List<PartIdentification> classpathPartTuples = Collections.singletonList(FilesetConstants.classpathPartIdentification);
		
		// if any additional types are set for the classpath file set, parse and add them to its relevant part tuples
		if (classpathTypeProperty != null) {
			List<PartIdentification> specifiedPartTuples = RelevantPartTupleProcessor.parseRelevantPartTuplesFromString(classpathTypeProperty);
			classpathPartTuples = RelevantPartTupleProcessor.mergeRelevantPartTuplesForEnricher(classpathPartTuples, specifiedPartTuples);
		}
		List<FileSet> filesets = partTuplesToFileSetMap.computeIfAbsent(classpathPartTuples, r -> new ArrayList<>());
		filesets.add( classpathFileSet);		
	}
	
	/**
	 * checks if a {@link Part} matches one (or more) of the {@link PartIdentification}s of a {@link FileSetTarget}, and if so
	 * adds it to the {@link FileSet}. If classes is true, it also looks for *-classes.jar
	 * @param part - the {@link Part} of the {@link Solution} to check
	 * @param classes - true if the jar is not simple :jar, but actually classes:jar
	 */
	public void matchPart( Part part) {
		for (Entry<List<PartIdentification>, List<FileSet>> entry : partTuplesToFileSetMap.entrySet()) {
			List<PartIdentification> partTuples = entry.getKey();
			if (
					RelevantPartTupleProcessor.contains(partTuples, part) 
				) {
				List<FileSet> sets = entry.getValue();
				sets.stream().forEach( s -> {				
					addToSet( s, part);
					Integer i = fileSetToSizeMap.get(s);
					if (i == null) 
						fileSetToSizeMap.put( s, 1);
					else 
						fileSetToSizeMap.put( s, ++i);
					}
				);
			}
		}
	}
	/**
	 * simply run through the stored {@link FileSet} and attach them to the {@link Project},
	 * if a {@link Path} reference is attached to the fileset, also export that. 
	 * 
	 * @param project - the {@link Project} as set be ant 
	 */
	public void produceFileSets(Project project) {
		for (Entry<FileSet, String> entry : fileSetToIdMap.entrySet()) {
				FileSet set = entry.getKey();
				String id = entry.getValue();
				// if the set's empty, we must exclude everything.. 
				Integer size = fileSetToSizeMap.get( set);
				if (
						size == null ||
						size == 0
					) {
					set.createExclude().setName("**/*");
				}
				boolean addFileSetReference = true;
				// we might have a path attached .. 
				String pathId = idToPathIdMap.get( id);
				if (pathId != null) {
					Path path = new Path( project);
					path.addFileset( set);
					project.addReference( pathId, path);
					if (pathId.equals(id))
						addFileSetReference = false;
				}
				
				if (addFileSetReference) {
					project.addReference( id, set);
				}
		}
	}
	
	/**
	 * add a {@link Part} to a {@link FileSet}
	 * @param set - the {@link FileSet} to add to
	 * @param part - the {@link Part} to add 
	 */
	private void addToSet( FileSet set, Part part) {
		Resource resource = part.getResource();
		
		if (!(resource instanceof FileResource)) 
			throw new IllegalStateException("Unexpected resource type [" + resource + "]. Expected a FileResource.");
		
		FileResource fileResource = (FileResource)resource;
		
		String name = fileResource.getPath();
		name = name.substring( directory.getAbsolutePath().length() );
		if (name.startsWith(this.directoryDelimiter)) {
			name = name.substring(this.directoryDelimiter.length());
		}
		log.debug("Adding solution part [" + name + "] to file set [" + fileSetToIdMap.get( set) + "]");
		set.createInclude().setName(name);
	}
	
	/**
	 * iterates over all fileset targets and merges the relevant part tuples (to be passed to the enricher)
	 * @return - a list of all unique part tuples as found in all file set targets.
	 */
	public List<PartIdentification> getRelevantPartTuples() {
		List<PartIdentification> mergedPartTuples = new ArrayList<PartIdentification>();
	
		for (Entry<List<PartIdentification>, List<FileSet>> entry : partTuplesToFileSetMap.entrySet()) {
			List<PartIdentification> partTuples = entry.getKey();
			mergedPartTuples = RelevantPartTupleProcessor.mergeRelevantPartTuplesForEnricher(mergedPartTuples, partTuples);
		}
		return mergedPartTuples;
	}
}
