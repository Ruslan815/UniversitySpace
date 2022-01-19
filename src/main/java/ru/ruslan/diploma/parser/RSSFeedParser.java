package ru.ruslan.diploma.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import ru.ruslan.diploma.model.Feed;
import ru.ruslan.diploma.model.FeedMessage;

public class RSSFeedParser {
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LANGUAGE = "language";
    static final String COPYRIGHT = "copyright";
    static final String LINK = "link";
    static final String AUTHOR = "author";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";

    final URL url;

    public RSSFeedParser(String feedUrl) throws MalformedURLException {
        this.url = new URL(feedUrl);
    }

    public Feed readFeed() throws IOException, XMLStreamException {
        Feed feed = null;
        boolean isFeedHeader = true;
        // Set header values initial to the empty string
        String description = "";
        String title = "";
        String link = "";
        String language = "";
        String copyright = "";
        String author = "";
        String pubdate = "";

        // First create a new XMLInputFactory
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        // Setup a new eventReader
        InputStream in = read();
        XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
        // read the XML document
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                String localPart = event.asStartElement().getName()
                        .getLocalPart();
                switch (localPart) {
                    case ITEM:
                        if (isFeedHeader) {
                            isFeedHeader = false;
                            feed = new Feed(title, link, description, language,
                                    copyright, pubdate);
                        }
                        eventReader.nextEvent();
                        break;
                    case TITLE:
                        title = getCharacterData(eventReader);
                        break;
                    case DESCRIPTION:
                        description = getCharacterData(eventReader);
                        break;
                    case LINK:
                        link = getCharacterData(eventReader);
                        break;
                    case LANGUAGE:
                        language = getCharacterData(eventReader);
                        break;
                    case AUTHOR:
                        author = getCharacterData(eventReader);
                        break;
                    case PUB_DATE:
                        pubdate = getCharacterData(eventReader);
                        break;
                    case COPYRIGHT:
                        copyright = getCharacterData(eventReader);
                        break;
                }
            } else if (event.isEndElement()) {
                if (Objects.equals(event.asEndElement().getName().getLocalPart(), ITEM)) {
                    FeedMessage message = new FeedMessage();
                    message.setAuthor(author);
                    message.setLink(link);
                    message.setTitle(title);
                    Objects.requireNonNull(feed).getMessages().add(message);
                    eventReader.nextEvent();
                }
            }
        }
        return feed;
    }

    private String getCharacterData(XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        XMLEvent event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private InputStream read() throws IOException {
        return url.openStream();
    }
}
